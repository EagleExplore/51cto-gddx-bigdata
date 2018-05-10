package com.Spark_les2

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/3.
  */
object JoinTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

    val table1 = sc.parallelize(List(("k1",1),("k2",2),("k3",3)))  //两个字段
    val table2 = sc.parallelize(List(("k1",10),("k2",20),("k4",30)))

    table1.join(table2).foreach(println)  //("k1",(1,10)),("k2",(2,20))
    println("-----")
    table1.leftOuterJoin(table2).foreach(println)
    println("-----")
    table1.rightOuterJoin(table2).foreach(println)

  }
}
