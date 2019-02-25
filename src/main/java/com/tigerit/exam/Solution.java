package com.tigerit.exam;


import java.util.*;

import static com.tigerit.exam.IO.*;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
    @Override
    public void run() {
        Integer T = readLineAsInteger();
        int caseNo = 1;
        while(T > 0) {
            T = T - 1;

            Integer nT = readLineAsInteger();
            List<Table> tableList = new ArrayList<>();
            while(nT > 0 ) {
                nT = nT - 1;

                Table table = new Table();
                table.name = readLine();

                String dimensionInput = readLine();
                Scanner dimensionInputScanner = new Scanner(dimensionInput);
                table.numOfColumns = dimensionInputScanner.nextInt();
                table.numOfRecords = dimensionInputScanner.nextInt();

                String columnInput = readLine();
                table.columns = new ArrayList<>(Arrays.asList(columnInput.split(" ")));

                table.data = new ArrayList<>();
                for(int i = 0; i<table.numOfRecords; i++) {
                    String dataInput = readLine();
                    Scanner dataInputScanner = new Scanner(dataInput);
                    List<Integer> rowDataList = new ArrayList<>();
                    while(dataInputScanner.hasNextInt()) {
                        rowDataList.add(dataInputScanner.nextInt());
                    }
                    table.data.add(rowDataList);
                }

                tableList.add(table);
            }

            List<Query> queryList = new ArrayList<>();
            Integer nQ = readLineAsInteger();
            while(nQ > 0) {
                nQ = nQ - 1;

                Query query = new Query();

                String line1 = readLine();
                query.fields = new ArrayList<>();
                List<String> line1List = Arrays.asList(line1.split(" "));
                if(line1List.get(1).equals("*")) {
                    query.fields.add("*");
                } else {
                    for(String s: line1List) {
                        if(!s.equals("SELECT")) {
                            query.fields.add(s.replace(",", ""));
                        }
                    }
                }

                String line2 = readLine();
                List<String> line2List = Arrays.asList(line2.split(" "));
                for(Table t: tableList) {
                    if(t.name.equals(line2List.get(1))) {
                        query.tableLeft = t;
                        break;
                    }
                }
                if(line2List.size() == 3) {
                    query.shortNameForLeft = line2List.get(2);
                } else {
                    query.shortNameForLeft = null;
                }

                String line3 = readLine();
                List<String> line3List = Arrays.asList(line3.split(" "));
                for(Table t: tableList) {
                    if(t.name.equals(line3List.get(1))) {
                        query.tableRight = t;
                        break;
                    }
                }
                if(line3List.size() == 3) {
                    query.shortNameForRight = line3List.get(2);
                } else {
                    query.shortNameForRight = null;
                }

                String line4 = readLine();
                List<String> line4List = Arrays.asList(line4.split(" "));
                String leftCondition = line4List.get(1);
                String rightCondition = line4List.get(3);

                List<String> leftList = Arrays.asList(leftCondition.split("\\."));
                if(query.shortNameForLeft != null && query.shortNameForLeft.equals(leftList.get(0)) || query.tableLeft.name.equals(leftList.get(0))) {
                    query.leftColumnForJoin = leftList.get(1);
                } else if(query.shortNameForLeft != null && query.shortNameForRight.equals(leftList.get(0)) || query.tableRight.name.equals(leftList.get(0))) {
                    query.rightColumnForJoin = leftList.get(1);
                }

                List<String> rightList = Arrays.asList(rightCondition.split("\\."));
                if(query.shortNameForLeft != null && query.shortNameForLeft.equals(rightList.get(0)) || query.tableLeft.name.equals(rightList.get(0))) {
                    query.leftColumnForJoin = rightList.get(1);
                } else if(query.shortNameForLeft != null && query.shortNameForRight.equals(rightList.get(0)) || query.tableRight.name.equals(rightList.get(0))) {
                    query.rightColumnForJoin = rightList.get(1);
                }

                queryList.add(query);
            }

            printLine("Test: " + caseNo);
            caseNo++;
            for(Query q: queryList) {
                Table t = q.generateResult();
                t.printTable();
            }
        }
    }
}

class Query {
    List<String> fields;
    Table tableLeft;
    String shortNameForLeft;
    Table tableRight;
    String shortNameForRight;
    String leftColumnForJoin;
    String rightColumnForJoin;

    Query() {
    }

    Table generateResult() {
        Table table = new Table();

        table.columns = new ArrayList<>();
        if(this.fields.size() == 1 && this.fields.get(0).equals("*")) {
            table.columns.addAll(tableLeft.columns);
            table.columns.addAll(tableRight.columns);
        } else {
            for(String s: this.fields) {
                table.columns.add(Arrays.asList(s.split("\\.")).get(1));
            }
        }

        int leftColumnIndex = tableLeft.columns.indexOf(this.leftColumnForJoin);
        int rightColumnIndex = tableRight.columns.indexOf(this.rightColumnForJoin);

        table.data = new ArrayList<>();
        for(List<Integer> leftRow: tableLeft.data) {
            for(List<Integer> rightRow: tableRight.data) {
                if(leftRow.get(leftColumnIndex) == rightRow.get(rightColumnIndex)) {
                    List<Integer> row = new ArrayList<>();
                    for(String column: table.columns) {
                        int idx = tableLeft.columns.indexOf(column);
                        if(idx != -1) {
                            row.add(leftRow.get(idx));
                        } else {
                            idx = tableRight.columns.indexOf(column);
                            row.add(rightRow.get(idx));
                        }
                    }
                    table.data.add(row);
                }
            }
        }

        return table;
    }
}

class Table {
    String name;
    Integer numOfColumns;
    Integer numOfRecords;
    List<String> columns;
    List<List<Integer>> data;

    Table() {
    }

    void printTable() {
        StringBuilder printString = new StringBuilder();
        boolean first = true;
        for(String s: this.columns) {
            if(first) {
                printString.append(s);
                first = false;
            } else {
                printString.append(" ").append(s);
            }
        }

        List<List<Integer>> printData = this.data;
        printData.sort((a1, a2) -> {
            for (int i = 0; i < Math.min(a1.size(), a2.size()); i++) {
                int ret = a1.get(i).compareTo(a2.get(i));
                if (ret != 0) {
                    return ret;
                }
            }
            return Integer.compare(a1.size(), a1.size());
        });

        for(List<Integer> row: printData) {
            printString.append("\n");
            first = true;
            for(Integer i: row) {
                if(first) {
                    printString.append(i);
                    first = false;
                } else {
                    printString.append(" ").append(i);
                }
            }
        }

        printString.append("\n");

        printLine(printString);
    }
}
