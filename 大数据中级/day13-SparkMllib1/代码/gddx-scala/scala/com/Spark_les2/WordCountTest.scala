package com.Spark_les2

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/3.
  */
object WordCountTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

    val rdd = sc.parallelize(List("a b c d e","a b c","a b d f"))
    //计算每个单词出现的次数：
    /**
      * a 3
      * b 3
      * c 2
      * d 2
      * e 1
      * f 1
      */
    val rdd2 = rdd.flatMap(_.split(" ")) //单元素RDD
                    .map(i=>(i,1))      //PairRDD
                      .reduceByKey(_ + _)
//                          .reduceByKey((x,y)=>x+y)

    println(rdd2.foreach(println))
  }
}
