package com.spark_sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.storage.StorageLevel

/**
  * Created by Administrator on 4/10.
  */
object VisitOperate {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val ss = SparkSession.builder()   //创建sparkSession 实例
      .config(conf)
      .getOrCreate()
    val sc = ss.sparkContext
    import ss.implicits._
//    sc.textFile("F:\\GDDX大数据2\\【GDDX-大数据中级-Days6】Spark Core综合实战\\电商流量数据文件\\data\\2015082818")
    val rdd = sc.textFile("hdfs://master:8020/user/hive/warehouse/track_log/date=2015-08-28/hour=18")
      .cache()        //缓存在内存和HDFS的/tmp/Spark*  路径下
      .filter(line => line.length>0)
      .map { line=>
        val arr = line.split("\\t")
        val url = arr(1)
        val guid = arr(5)
        val sessionId = arr(10)
        val date = arr(17).substring(0,10)    //2015-08-28 18:10:00
        (date,guid,sessionId,url)
      }.filter(i=>i._4.length>5)   // where length(url)>5
//      .partitionBy(new HashPartitioner(10))  // 把数据通过key date+"_"+guid 进行分区，相同date+"_"+guid 的数据会存储在同一个节点上
      .toDF("date","guid","sessionId","url")   //设置字段名
      .persist(StorageLevel.DISK_ONLY)     //持久化在内存和HDFS的/tmp/Spark*  路径下
      .createOrReplaceTempView("track_log")  //设置表名

    val sql =
      """
        |select count(distinct guid) uv,sum(pv) pv,
        |count(case when pv>=2 then sessionid else null end) second_num,
        |count(sessionid) visits
        |from
        |(select date,sessionid,max(guid) guid,count(url) pv from track_log
        |   where length(url)>0
        |group by date,sessionid ) a
        |group by date
      """.stripMargin
    ss.sql(sql).cache() //缓存
      .show(10)
    /**
      * +-----+-----+----------+------+
     |   uv|   pv|second_num|visits|
       +-----+-----+----------+------+
     |16065|35880|      6678| 16185|
     +-----+-----+----------+------+
      */
    sc.stop()
    ss.stop()
  }
}
