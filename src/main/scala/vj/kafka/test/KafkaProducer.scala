package vj.kafka.test

import kafka.producer._

import java.util.Properties

object KafkaProducer {
  
  val brokerList = "localhost:9092" //"localhost:9092,localhost:9093"
  val topic = "api"
  val props = new Properties()
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  props.put("metadata.broker.list", brokerList)
  val config = new ProducerConfig(props)
  val producer = new Producer[String, String](config)

  def main(args: Array[String]): Unit = {
    for (i <- 1 to 100) {
      val keys = KeyedMessage[String, String](
        topic,
        java.util.UUID.randomUUID().toString(),
        "hellow world " + i)
      producer.send(keys)
    }
  }

}