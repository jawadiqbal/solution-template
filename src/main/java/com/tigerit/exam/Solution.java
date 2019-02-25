package com.tigerit.exam;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        // your application entry point

        // sample input process
        // String string = readLine();

        // Integer integer = readLineAsInteger();

        // sample output process
        // printLine(string);
        // printLine(integer);

        Integer T = readLineAsInteger();
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

                // table.printTable();
                tableList.add(table);
            }

            // Integer nQ = readLineAsInteger();
            // while(nQ > 0) {
            //     nQ = nQ - 1;
            //
            //
            // }
        }
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

    Table(String name, Integer numOfColumns, Integer numOfRecords, List<String> columns, List<List<Integer>> data) {
        this.name = name;
        this.numOfColumns = numOfColumns;
        this.numOfRecords = numOfRecords;
        this.columns = columns;
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Table{");
        sb.append("name='").append(name).append('\'');
        sb.append(", numOfColumns=").append(numOfColumns);
        sb.append(", numOfRecords=").append(numOfRecords);
        sb.append(", columns=").append(columns);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }

    public void printTable() {
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

        for(List<Integer> row: this.data) {
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
