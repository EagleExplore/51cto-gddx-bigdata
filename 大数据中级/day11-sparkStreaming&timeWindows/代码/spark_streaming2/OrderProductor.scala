package com.spark_streaming2

import java.util.HashMap

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.util.Random

/**
  *
  */
object OrderProductor {
  def main(args: Array[String]) {
    val topic = "order"
    val brokers = "master:9092,slave1:9092"

    // Zookeeper connection properties
    val props = new HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    // 1s 生产10个订单
    while(true) {
      (1 to 10).foreach { messageNum =>
        //   地区id，订单id，订单金额，订单时间
        val str = messageNum+","+Random.nextInt(10)+","+Math.round(Random.nextDouble()*100)+","+DateUtils.getCurrentTime()

        val message = new ProducerRecord[String, String](topic, null, str)
        producer.send(message)
      }

      Thread.sleep(1000)
    }
  }
}
