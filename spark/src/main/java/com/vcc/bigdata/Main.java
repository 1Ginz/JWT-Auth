package com.vcc.bigdata;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;

import java.util.List;

public class Main {

    public static final String IPv4_PATTERN = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";


    public static void main(String[] args) {
        SparkSession spark = null;
        try {
            System.out.println("Hello World!");

            SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local");
            spark = SparkSession.builder().appName("Simple Application").config(conf).getOrCreate();

            Dataset<Row> csv = spark.read().option("header", "false").csv("C:\\Users\\ACER\\Desktop\\B19DCAT106_XXXX\\spark\\src\\data\\test.csv");
//            System.out.println("Initial row count: ======================================== " + csv.count());


            spark.udf().register("isIPv4", (UDF1<String, Boolean>) Main::isIPv4, DataTypes.BooleanType);
            spark.udf().register("convertToNet24", (UDF1<String, String>) Main::convertToNet24, DataTypes.StringType);

            Dataset<Row> ipv4 = csv.filter("isIPv4(`_c0`)").selectExpr("convertToNet24(`_c0`) as `net24`");


            System.out.println("Initial row count: ======================================== " + ipv4.count());

            Dataset<Row> result = spark.read().text("C:\\Users\\ACER\\Desktop\\B19DCAT106_XXXX\\spark\\src\\data\\result_v4_location");

            Dataset<Row> resultWithColumns = result.withColumn("ip", functions.split(result.col("value"), "\t").getItem(0))
                    .withColumn("code", functions.split(result.col("value"), "\t").getItem(1));

            spark.udf().register("isInNet24", (UDF2<String, List<String>, Boolean>) Main::isInNet24, DataTypes.BooleanType);

            Dataset<Row> joinv4 = ipv4.join(resultWithColumns, ipv4.col("net24").equalTo(resultWithColumns.col("ip")), "inner");
            long distinctCount = joinv4.select("ip").distinct().count();
            long allipvn = result.count();

            joinv4.show();
            System.out.println("Distinct count: ======================================== " +(float) distinctCount / allipvn * 100 + "%");
            spark.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (spark != null) {
                spark.close();
            }
        } finally {
            if (spark != null) {
                spark.close();
            }
        }




    }


    public static boolean isIPv4(String ip){
        return ip != null && ip.matches(IPv4_PATTERN);
    }

    public static String convertToNet24(String ip){
        String[] octet = ip.split("\\.");
        return octet[0] + "." + octet[1] + "." + octet[2] + ".000";
    }

    public static boolean isInNet24(String ip, List<String> net24){
        for(String s : net24){
            if (ip.equalsIgnoreCase(s)) return true;
        }
        return false;
    }


}