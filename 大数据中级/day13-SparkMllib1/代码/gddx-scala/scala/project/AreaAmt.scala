package project

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  */
object AreaAmt {

  def main(args: Array[String]) {
    //    if (args.length < 4) {
    //      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>")
    //      System.exit(1)
    //    }
    //
//    StreamingExamples.setStreamingLogLevels()
    //    val Array(zkQuorum, group, topics, numThreads) = args
    val zkQuorum = "slave1:2181"
    val group = "g1"
    val topics = "orderTopic"
    val numThreads = 2

    val dao = new HBaseImpl(zkQuorum)

    val sparkConf = new SparkConf().setAppName("StatelessWordCount").setMaster("local[2]")
      .set("spark.testing.memory", "571859200")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.checkpoint("hdfs://master:8020/user/root/checkpoint/AreaAmtProject") //设置有状态的检查点

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
//    lines.print(3)

    //产生我们需要的pairRDD
    // 2,6,70,2016-09-11 17:10:11
    val linerdd = lines.map {row=>
    {
      val arr = row.split(",")
      val key = arr(3).substring(0,10)+"_"+arr(0)   //2016-09-04_Areaid      ,扩展：继续细分到城市
      val amt = arr(2).toInt
      (key,amt)
    }}

    val addFunc = (currValues: Seq[Int], prevValueState: Option[Int])=> {
      //通过Spark内部的reduceByKey按key规约，然后这里传入某key当前批次的Seq,再计算每个key的总和
      val currentCount = currValues.sum
      // 已累加的值
      val previousCount = prevValueState.getOrElse(0)
      // 返回累加后的结果，是一个Option[Int]类型
      Some(currentCount + previousCount)
    }

    //变量Dstream的标准语法
    linerdd.updateStateByKey[Int](addFunc).foreachRDD{rdd =>    //Dstream RDD
      rdd.foreachPartition { partitionOfRecords =>
        partitionOfRecords.foreach { record =>
          println(record._1+"---"+record._2)
          dao.insert("order", record._1, "cf", "order_amt", record._2+"")

        }
      }
    }

    ssc.start()
    ssc.awaitTermination()
    }

  }
