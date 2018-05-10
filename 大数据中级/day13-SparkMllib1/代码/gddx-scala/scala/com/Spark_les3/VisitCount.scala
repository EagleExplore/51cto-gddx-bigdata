package com.Spark_les3

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
  * Created by Administrator on 4/8.
  */
object VisitCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

//    val rdd = sc.textFile("F:\\GDDX大数据2\\【GDDX-大数据中级-Days6】Spark Core综合实战\\电商流量数据文件\\data\\2015082818")
      val rdd = sc.textFile("hdfs://master:8020/user/hive/warehouse/track_log/date=2015-08-28/hour=18")
                 .cache()
                 .filter(line => line.length>0)
                  .map { line=>
                    val arr = line.split("\\t")
                    val url = arr(1)
                    val guid = arr(5)
                    val sessionId = arr(10)
                    val date = arr(17).substring(0,10)    //2015-08-28 18:10:00
                    (date+"_"+guid,url)
                  }.filter(i=>i._2.length>5)   // where length(url)>5
                .partitionBy(new HashPartitioner(10))  // 把数据通过key date+"_"+guid 进行分区，相同date+"_"+guid 的数据会存储在同一个节点上
                .persist(StorageLevel.DISK_ONLY)
    /**
      *  select date, count(distinct guid) uv, count(url) pv
      *   from table group by date
      */
    rdd.map(i=>(i._1,1)) //(date+"_"+guid,1)
      .reduceByKey(_ + _)  // (date+"_"+guid,pv)
        .map { i=>
         val date = i._1.split("_")(0)
         val guid_count = 1
         val pv = i._2
      (date,(guid_count,pv))
    }.reduceByKey((x,y)=>(x._1 + y._1,x._2 + y._2))
      .saveAsTextFile("hdfs://master:8020/user/root/VisitCount")

//      .foreach(println)     //  hive中 2015-08-28      16068   35880
                          //  (2015-08-28,(16068,35880))

    rdd.unpersist()

    sc.stop()
  }

}
