package com.kk.autocode.base;

import java.util.Scanner;

public class ColonProcess {

    @SuppressWarnings("resource")
    public static void processColoum() {
        Scanner scan = new Scanner(System.in);
        System.out.println("当前需转换列的信息:");
        do {
            String line = scan.nextLine();
            int endIndex = line.lastIndexOf(",");
            String end = null;
            if (endIndex != -1) {
                line = line.substring(0, line.length() - 1);
            }

            int index = line.indexOf(".");
            if (index != -1) {
                end = line.substring(index + 1);
            } else {
                end = line;
            }

            int fstmp = end.indexOf("_");
            String timeValue = null;
            if (fstmp != -1) {
                timeValue = end.substring(0, fstmp) + end.substring(fstmp + 1, fstmp + 2).toUpperCase()
                        + end.substring(fstmp + 2);
            } else {
                timeValue = end;
            }

            if (endIndex != -1) {
                System.out.println(line + " as " + timeValue + ",");
            } else {
                System.out.println(line + " as " + timeValue);
            }

        } while (scan.hasNext());
    }

    public static void main(String[] args) {
        processColoum();
    }
}
