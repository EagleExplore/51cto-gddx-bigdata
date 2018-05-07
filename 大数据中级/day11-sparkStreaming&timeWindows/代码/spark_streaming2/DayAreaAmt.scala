package com.spark_streaming2

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  */
object DayAreaAmt {

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
    val ssc = new StreamingContext(sparkConf, Seconds(10)) // 窗口时长和滑动步长相等
    ssc.checkpoint("hdfs://master:8020/user/root/checkpoint/DayAreaAmt") //设置有状态的检查点，存储总数值

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)

    val words = lines.map { row =>
        val arr = row.split(",")
        val day = arr(3).substring(0,10)
        val area = arr(0)
        val amt = arr(2).toInt
      ((day,area),amt)
    }
    val addFunc = (currValues: Seq[Int], prevValueState: Option[Int]) => {
      //通过Spark内部的reduceByKey按key归约，然后这里传入某key当前批次的Seq,再计算每个key的总和
      val currentCount = currValues.sum
      // 已累加的值
      val previousCount = prevValueState.getOrElse(0)
      // 返回累加后的结果，是一个Option[Int]类型
      Some(currentCount + previousCount)
    }

    words.updateStateByKey[Int](addFunc).print()  //对pairRDD里的每个key的values进行addFunc处理

      ssc.start()
      ssc.awaitTermination()
    }

  }
