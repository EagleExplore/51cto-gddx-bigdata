package com.Spark_les1

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/1.
  */
object RDDTest {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("test").setMaster("local")
    //sparkConf.set("spark.testing.memory", "571859200")
    val sc = new SparkContext(conf)   //Driver类

    val rdd = sc.parallelize(List(1,2,3,4,56,6,7))  //seq

    println(rdd.getClass)

    //transform
    val rdd1 = rdd.map(i=>(i,1))
    //二元RDD，PairRDD（类似Map）

//action
    rdd.max()
    rdd.min()
    rdd.count()











  }

}
