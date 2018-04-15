package com.spark_sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * 直接操作hive，完成流量统计案例
  */

object HiveOper {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("hiveOper").setMaster("local")
    sparkConf.set("spark.testing.memory", "471859200")
    val ss = SparkSession.builder()
        .enableHiveSupport()  //增加对Hive操作的支持
        .config(sparkConf)
        .getOrCreate()

    val date = "2015-08-28"    //通常是通过参数传进来
    //insert overwrite table daily_visit partition (date='$date')
    val sqlStr =
      s""" select count(distinct guid) uv,sum(pv) pv,
          |count(case when pv>=2 then sessionid else null end) second_num,
          |count(sessionid) visits from
          |(select ds date,sessionid,max(guid) guid,count(url) pv from track_log where ds='$date' and hour='18'
          |and length(url)>0
          |group by ds,sessionid) a
          |group by date
       """.stripMargin

    ss.sql(sqlStr).show(10)

    ss.stop()

  }
}
