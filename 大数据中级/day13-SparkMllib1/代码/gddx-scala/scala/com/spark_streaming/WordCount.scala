package com.spark_streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  */
object WordCount {

  def main(args: Array[String]) {
    //    if (args.length < 4) {
    //      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>")
    //      System.exit(1)
    //    }
    //
    //    val Array(zkQuorum, group, topics, numThreads) = args
    val zkQuorum = "slave1:2181"
    val group = "g1"                  //对kafka来讲，groupid的作用是什么？
    val topics = "log"
    val numThreads = 1

    val sparkConf = new SparkConf()
      .setAppName("WordCount")
      .setMaster("local[2]")   //核数至少给2，如果只是1的话，无法进行数据计算。
      .set("spark.testing.memory", "571859200")

    val ssc = new StreamingContext(sparkConf, Seconds(2))   //2s一个批次

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
//    val topicMap2 = Map(topics->2)

    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)   //每一个message或称作时间段批次

    val words = lines.flatMap(_.split(" "))    //1 6 0 7 9 1 9 5 8 5
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)    //得到每个批次中word,count
    wordCounts.print(3)  //每个批次打印10行

    wordCounts.foreachRDD(rdd =>
    {
      rdd.foreachPartition( p =>{
        p.foreach(println)
      }
      )
    })
    ssc.start()
    ssc.awaitTermination()
  }
}
