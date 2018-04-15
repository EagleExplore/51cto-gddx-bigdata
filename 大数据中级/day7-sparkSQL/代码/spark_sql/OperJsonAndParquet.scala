package com.spark_sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  *  Spark 安装包spark-2.2.0-bin-hadoop2.6\examples\src\main\resources  下有数据文件
  */
object OperJsonAndParquet {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("OperJsonAndParquet").setMaster("local").set("spark.testing.memory", "571859200")

    val ss = SparkSession.builder()
      .config(sparkConf)
      .getOrCreate()

    val sc = ss.sparkContext

    ss.read.json("file:///F:\\GDDX大数据2\\所需软件\\spark-2.2.0-bin-hadoop2.6\\examples\\src\\main\\resources\\people.json")
      .createOrReplaceTempView("people")  //默认读HDFS，读本地文件需要file://
    val rs = ss.sql("select * from people")
    rs.printSchema()
    rs.show()   //展示结果集

    ss.read.parquet("file:///F:\\GDDX大数据2\\所需软件\\spark-2.2.0-bin-hadoop2.6\\examples\\src\\main\\resources\\users.parquet")
      .createOrReplaceTempView("users")
    val rs2 = ss.sql("select * from users")
    rs2.printSchema()
    rs2.show()

//    ss.read.text("")


    sc.stop()
    ss.stop()

  }
}
