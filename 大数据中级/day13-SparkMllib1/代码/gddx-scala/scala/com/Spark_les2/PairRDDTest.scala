package com.Spark_les2

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/3.
  */
object PairRDDTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

    val rdd = sc.parallelize(List((2,3),(4,5),(3,2),(2,1)))
    val rdd2 = sc.parallelize(List(2,3,5)).map(i=>(i,i+2))  //主要方式

    rdd2.map {
      case (k, v) => (k, v + 1)
    }  //i=>i+1 返回单元素，rdd2执行map函数需要返回和RDD2相同（PairRDD）类型
    rdd2.filter {
      case (k, v) => v%2==0
    }
//(2,3),(4,5),(3,2),(2,1)  -》(2,4),(4,6),(3,3),(2,2)
//    rdd.mapValues(i=>i+1).foreach(println _)
    rdd.reduceByKey(_ + _).foreach(println _)  //(2,3),(4,5),(3,2),(2,1) ->(2,4),(4,5),(3,2)
//    rdd.groupByKey()



  }
}
