package com.kk.buildsql;

public class OutSqlProcess {

    public static void main(String[] args) {
        outOne();
    }

    private static void outOne() {
        StringBuilder show = new StringBuilder();

        // show.append("values(1,2,'code',1,1,0);");
        // 进行二楼的输出
        // 第一排
        int i = 200;
        int index = 1;
        while (i <= 289) {
            show.append("insert into C_OR_SEAT(SE_ID,OR_ID,SE_CODE,SE_COORX,SE_COORY,SE_STATE)");
            show.append("values(");
            show.append(String.valueOf(i)).append(",");
            show.append("3").append(",");
            show.append("'code'").append(",");
            show.append("1").append(",");
            show.append(index).append(",");
            show.append("0").append(");");

            i++;

            System.out.println(show.toString());

            show = new StringBuilder();
        }
    }

    private static void outTwo() {
        StringBuilder show = new StringBuilder();
        int i = 3;
        int index = 1;
        while (i < 24) {
            show.append("insert into C_OR_SEAT(SE_ID,OR_ID,SE_CODE,SE_COORX,SE_COORY,SE_STATE)");
            show.append("values(");
            show.append(String.valueOf(i)).append(",");
            show.append("2").append(",");
            show.append("'code'").append(",");
            show.append("2").append(",");
            show.append(index).append(",");
            show.append("0").append(");");

            if (i % 2 == 0) {
                i += 2;
            }

            i += 1;
            index++;

            System.out.println(show.toString());

            show = new StringBuilder();
        }
    }

}
