package com.sparkSql

import java.util.regex.{Matcher, Pattern}

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel


object UseUDF {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("useUDF").setMaster("local")
    val ss = SparkSession.builder().config(sparkConf).getOrCreate()
    val sc = ss.sparkContext


    //读HDFS文件
//    val fileRDD = sc.textFile("hdfs://192.168.75.130:8020/user/hive/warehouse/track_log/ds=2015-08-28/hour=18/").cache()
      val fileRDD = sc.textFile("file:///E:\\Spark-2.0\\快学Spark 2.0\\data\\2015082818")
      .filter(line=>line.length>0)
      .map{ line =>
        var arr = line.split("\t")    //hive表默认列分隔符是\001
        var date = arr(17).substring(0,10)        //trackTime  的格式 2015-08-28 18:10:00
        var guid = arr(5)
        val url = arr(1)
        (date,guid,url)   //Tuple3
      }.filter(i=>i._3.length>0).persist(StorageLevel.DISK_ONLY)


    //开发一个同getTopic的UDF
    ss.udf.register("getName",(url:String,regex:String) =>
    {
      val p: Pattern = Pattern.compile(regex)
      val m: Matcher = p.matcher(url)
      if (m.find)
        m.group(0).split("/")(1).toLowerCase
      else
        null
    }
    )

    import ss.implicits._
    import ss.sql
    //统计所有活动页的流量
    fileRDD.toDF("date","guid","url").createOrReplaceTempView("log")

    val sql1=
      s"""select date,getName(url,'sale/[a-zA-Z0-9]+'),count(distinct guid),count(url) from log
         |where url like '%sale%' group by date,getName(url,'sale/[a-zA-Z0-9]+')
       """.stripMargin
    sql(sql1).rdd.foreach(println)



    sc.stop()
    ss.stop()

  }
}
