package vj.kafka.test

import kafka.javaapi.consumer.SimpleConsumer
import kafka.api.FetchRequest

import kafka.consumer.Consumer
import kafka.consumer.ConsumerConfig
import java.util.Properties
import kafka.message.Message
//import kafka.javaapi.OffsetRequest

object Runner {

  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 2181
    val socketTimeoutMs = 5000
    val bufferSizeBytes = 1024*1024
    val clientId = "VJ_TEST_CLIENT"
    val simpleConsumer = new SimpleConsumer(host, port, socketTimeoutMs, bufferSizeBytes, clientId)
    //val offsetReq = new OffsetRequest()
    val topic = "api"
    val partition = 0
    val time = System.currentTimeMillis() - 1000
    //simpleConsumer.getOffsetsBefore(topic, partition, time, maxNumOffsets = 1)
    //val fetchReq = new FetchRequest(topic, partition, 0L, 100)
    //simpleConsumer.fetch(fetchReq)

    val props = new Properties()
    props.put("zookeeper.connect", "localhost:2181")
    props.put("group.id", "1")
    props.put("zk.sessiontimeout.ms", "5000")
    props.put("zk.synctime.ms", "200")
    props.put("autocommit.interval.ms", "1000")
    val config = new ConsumerConfig(props)
    val consumerConnector = Consumer.createJavaConsumerConnector(config)
    //val consumerMap = consumerMap
    val topicCountMap = new java.util.HashMap[String, Integer]
    topicCountMap.put("api", new Integer(1))
    val consumerMap = consumerConnector.createMessageStreams(topicCountMap)
    val consumerIterator = consumerMap.get(topic).iterator()
    var i = 1
    while(consumerIterator.hasNext()) {
      val kafkaStream = consumerIterator.next()
      val msgIterator = kafkaStream.iterator()
      while (msgIterator.hasNext()) {
        val msg = msgIterator.next()
        println(i + "::topic: " + msg.topic + " message: " + new String(msg.message) + " key: " + msg.key + " partition: " + msg.partition + " offset:" + msg.offset)
        
      }
      i+= 1
    }

  }

}
