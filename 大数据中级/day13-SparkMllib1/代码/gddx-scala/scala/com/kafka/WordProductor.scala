//package com.kafka
//
//import java.util.HashMap
//
///**
//  *
//  */
//object WordProductor {
//  def main(args: Array[String]) {
////    if (args.length < 4) {
////      System.err.println("Usage: KafkaWordCountProducer <metadataBrokerList> <topic> " +
////        "<messagesPerSec> <wordsPerMessage>")
////      System.exit(1)
////    }
////
////    val Array(brokers, topic, messagesPerSec, wordsPerMessage) = args
//
//    val topic = "logTopic"
//    val brokers = "master:9092,slave1:9092"
//    val messagesPerSec = 10   //每秒生产10个message
//    val wordsPerMessage = 10  //每个message含10个word
//
//    // Zookeeper connection properties
//    val props = new HashMap[String, Object]()
//    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
//    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
//    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
//
//    val producer = new KafkaProducer[String, String](props)
//
//    // Send some messages
//    while(true) {
//      (1 to messagesPerSec.toInt).foreach { messageNum =>
//        val str = (1 to wordsPerMessage.toInt).map(x => scala.util.Random.nextInt(10).toString)
//          .mkString(" ")
//
//        val message = new ProducerRecord[String, String](topic, null, str)
//        producer.send(message)
//      }
//
//      Thread.sleep(1000)
//    }
//  }
//}
