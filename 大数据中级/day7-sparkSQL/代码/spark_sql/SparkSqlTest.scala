package com.spark_sql

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/10.
  */
case class Person(id:Int,name:String)

object SparkSqlTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
//    val sc = new SparkContext(conf)   //Driver类
    val ss = SparkSession.builder()   //创建sparkSession 实例
//               .appName("test").master("local")
               .config(conf)
                 .getOrCreate()
//    val sc = ss.sparkContext
    val rdd = ss.createDataFrame((0 to 99).map(i=>Person(i,"name"+i)))
    rdd.createOrReplaceTempView("person")  //表名

    val result = ss.sql("select * from person")
//    result.show(10)   //预览内容
    println("------------------")
    //提问：字段名是什么，即属性名id和name
//    result.printSchema()
    // 遍历result
//    result.rdd  //把DataFrame转为rdd
//      .map { row =>
//      val id =  row.getAs[Int]("id")
//      val name = row.getString(1) //row.getAs[String]("name")
//      (id,name)
//    }.foreach(println(_))

    result.rdd.map {
      case Row(mid:Int,mname:String) => (mid,mname)
    }.foreach(println(_))


    ss.stop()

  }
}
