package com.Spark_les2

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/3.
  */
object Test {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

    val rdd = sc.parallelize(List(1,2,3,4,56,6,7)).persist(StorageLevel.MEMORY_ONLY)  //seq

    println(rdd.getClass)
    println(rdd.map(i=>(i,i+1)).getClass)
//释放RDD的存储
    rdd.unpersist()

    //创建一个PairRDD
    val pairrdd = sc.parallelize(List((1,(2,3)),(3,(5,4)),("k",("v",1))))

//    pairrdd.reduceByKey()
    /**
      * select sum(amt) from aa group by date,hour,cityID
      *
      * 根据需要构造RDD
      * (date_hour_cityID,(amt,,,,,))
      */


    val rdd1 = sc.parallelize(List(1,2,3))
    val rdd2 = sc.parallelize(List(3,4,5))
    rdd2.collect().foreach(println)  //集群模式执行的话，通常用数据之前需要调 rdd2.collect()
    rdd2.foreach(println)
  }
}
