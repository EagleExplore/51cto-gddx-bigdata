package com.Spark_les2

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/3.
  */
object AvgTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

    val rdd = sc.parallelize(List((3,4),(2,4),(3,2),(2,6)))
    rdd.mapValues(i=>(i,1)) //(3,(4,1)),(2,(4,1))....
         .reduceByKey((x,y)=>(x._1 + y._1,x._2 + y._2))  // PairRDD的两两相加reduceByKey((x,y)=>x+y)
                                                        // 单元素RDD的两两相加reduceByKey((x,y)=>x+y)
           .mapValues(i=>i._1/i._2)
      .foreach(println)
  }
}
