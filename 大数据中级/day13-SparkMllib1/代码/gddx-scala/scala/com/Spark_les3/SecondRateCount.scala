package com.Spark_les3

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/8.
  */
object SecondRateCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

    val rdd = sc.textFile("F:\\GDDX大数据2\\【GDDX-大数据中级-Days6】Spark Core综合实战\\电商流量数据文件\\data\\2015082818")
      .filter(line => line.length>0)
      .map { line=>
        val arr = line.split("\\t")
        val url = arr(1)
        val guid = arr(5)
        val sessionId = arr(10)
        val date = arr(17).substring(0,10)    //2015-08-28 18:10:00
        (date,sessionId,url)
      }.filter(i=>i._3.length>5)   // where length(url)>5
      .persist(StorageLevel.DISK_ONLY)

    rdd.map(i=>(i._1+"_"+i._2,1))   //date_sessionId , 1
      .reduceByKey(_ + _ ) // date_sessionId , pv   ,   select count(url) pv,session_id from  track_log where date='2018-01-01' and lengnth(url)>5 group by session_id
        .map { i => {
            val arr = i._1.split("_")
            val date = arr(0)
            val fenmu = if(arr(1).length>0) 1 else 0    // 只要sessionID 非0的
            val fenzi = if(i._2 >= 2) 1 else 0
          (date,(fenmu,fenzi))
        }
      }.reduceByKey((x,y)=>(x._1 + y._1,x._2 + y._2))
      .foreach(println)  //  (2015-08-28,(16185,6678))
                         // hive  2015-08-28     16185      6678

    rdd.unpersist()

    sc.stop()

  }
}
