package com.spark_streaming2

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

/**
  *
  */
object WindowAreaAmt {

  def main(args: Array[String]) {
    //    if (args.length < 4) {
    //      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>")
    //      System.exit(1)
    //    }
    //
//        val Array(zkQuorum, group, topics, numThreads) = args
    val zkQuorum = "slave1:2181"
    val group = "g1"
    val topics = "order"
    val numThreads = 2

    val sparkConf = new SparkConf().setAppName("StatelessWordCount").setMaster("local[2]")
      .set("spark.testing.memory", "571859200")
    val ssc = new StreamingContext(sparkConf, Seconds(5)) // 窗口时长和滑动步长相等
    ssc.checkpoint("hdfs://master:8020/user/root/checkpoint/DayAreaAmt") //设置有状态的检查点，存储总数值

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)

    val words = lines.map { row =>
        val arr = row.split(",")
        val day = arr(3).substring(0,10)
        val area = arr(0)
        val amt = arr(2).toInt
      ((day,area),amt)
    }.reduceByKeyAndWindow(
      _ + _,  //加上新进入窗口的批次中的元素
      _ - _,  //移除离开窗口的老批次中的元素
      Seconds(60),    //窗口时长
      Seconds(5), //滑动步长
      2)
      .print(50)

    ssc.start()
      ssc.awaitTermination()
    }

  }
