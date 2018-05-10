package com.Spark_les2

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/3.
  */
object FileWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

    val rdd = sc.textFile("file:///c:/spark.txt")//本地文件 或 HDFS文件  hdfs://ip:8020/...

    println(rdd.count()) //文件行数

    println( rdd.first())

    rdd.flatMap(_.split(" "))  //单元素RDD
      .map(i=>(i,1))      //PairRDD
      .reduceByKey(_ + _)
      .foreach(println _)
  }
}
