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
                    printLine(rowDataList);
                    table.data.add(rowDataList);
                }

                tableList.add(table);
            }

            printLine(tableList);
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
        return "Table{" +
                "name='" + name + '\'' +
                ", numOfColumns=" + numOfColumns +
                ", numOfRecords=" + numOfRecords +
                ", columns=" + columns +
                ", data=" + data +
                '}';
    }
}
