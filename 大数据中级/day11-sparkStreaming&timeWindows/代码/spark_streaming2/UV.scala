package com.spark_streaming2

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object UV {

  /**
    * count(dsintct guid) group by date
    * 借助set完成去重，检查点里存全部guid
    *
    */
    def main(args: Array[String]) {
      val checkpointDirectory = "hdfs://master:8020/user/root/checkpoint/uv"
      val ssc = StreamingContext.getOrCreate(checkpointDirectory,
        () => {
          val topics = "logTopic"
          val zkQuorum = "slave1:2181"
          val group = "g1"
          val sparkConf = new SparkConf().setAppName("uv").setMaster("local[2]").set("spark.testing.memory", "571859200")
          val ssc = new StreamingContext(sparkConf, Seconds(2))
          ssc.checkpoint(checkpointDirectory)
          val topicMap = topics.split(",").map((_, 2)).toMap
          val messages = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)
          computeUv(messages)
          ssc
        })

      ssc.start()
      ssc.awaitTermination()
    }

  def updateUvState(values:Seq[Set[String]], prevValueState:Option[Set[String]]) : Option[Set[String]] = {
    //Set(guid)
    val defaultState = Set[String]()
    values match {
      case Nil => Some(prevValueState.getOrElse(defaultState))  //如果为空（首个批次）. Nil是一个空的List,定义为List[Nothing]
      case _ =>
        val guidSet = values.reduce(_ ++ _)  //set与set拼接用++ ，set里的元素自动去重。
//        println("11111-"+prevValueState.getOrElse(defaultState).size)
//        println("22222-"+guidSet.size)
        Some(prevValueState.getOrElse(defaultState) ++ guidSet)
    }
  }

  def computeUv(messages:  InputDStream[(String, String)] ) = {
    val sourceLog = messages.map(_._2)  //messages里是Touple2，第二列是数据。  2016-09-10 12:42:38,id_44,www.yhd.com/aa7

    val utmUvLog = sourceLog.filter(_.split(",").size==3)
      .map(logInfo => {
      val arr = logInfo.split(",")
      val date = arr(0).substring(0,10)
      val guid = arr(1)
      //      val url = arr(2)
      (date,Set(guid))
    }).persist(StorageLevel.MEMORY_AND_DISK_SER)

    val utmDayActive = utmUvLog.updateStateByKey(updateUvState) // date,set(guid)
        .map(result => {
      (result._1, result._2.size)    // date,UV
    }).print()


    //遍历和查看结果

  }
}
