package com.spark_streaming2

import java.util.HashMap

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.util.Random

/**
  *
  */
object LogProductor {
  def main(args: Array[String]) {
//    if (args.length < 4) {
//      System.err.println("Usage: KafkaWordCountProducer <metadataBrokerList> <topic> " +
//        "<messagesPerSec> <wordsPerMessage>")
//      System.exit(1)
//    }
//
//    val Array(brokers, topic, messagesPerSec, wordsPerMessage) = args

    val topic = "logTopic"
    val brokers = "master:9092,slave1:9092"
    val messagesPerSec = 10   //每秒生产10个message
    val wordsPerMessage = 10  //每个message含10个word

    // Zookeeper connection properties
    val props = new HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    // 1s 生产10行
    while(true) {
      (1 to 10).foreach { messageNum =>
        //   time，guid，url
        val str = DateUtils.getCurrentTime()+",id_"+Random.nextInt(300)+","+"www.yhd.com/aa"+messageNum

        val message = new ProducerRecord[String, String](topic, null, str)
        producer.send(message)
      }

      Thread.sleep(1000)
    }
  }
}
