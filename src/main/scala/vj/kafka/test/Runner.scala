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
    val host = "10.29.29.208"
    val port = 2181
    val socketTimeoutMs = 5000
    val bufferSizeBytes = 1024*1024
    val clientId = "VJ_TEST_CLIENT"
    val simpleConsumer = new SimpleConsumer(host, port, socketTimeoutMs, bufferSizeBytes, clientId)
    //val offsetReq = new OffsetRequest()
    val topic = "comments"
    val partition = 0
    val time = System.currentTimeMillis() - 1000
    //simpleConsumer.getOffsetsBefore(topic, partition, time, maxNumOffsets = 1)
    //val fetchReq = new FetchRequest(topic, partition, 0L, 100)
    //simpleConsumer.fetch(fetchReq)

    val props = new Properties()
    props.put("zookeeper.connect", "10.29.29.208:2181")
    props.put("group.id", "1")
    props.put("zk.sessiontimeout.ms", "5000")
    props.put("zk.synctime.ms", "200")
    props.put("autocommit.interval.ms", "1000")
    val config = new ConsumerConfig(props)
    val consumerConnector = Consumer.createJavaConsumerConnector(config)
    //val consumerMap = consumerMap
    val topicCountMap = new java.util.HashMap[String, Integer]
    topicCountMap.put("comments", new Integer(1))
    val consumerMap = consumerConnector.createMessageStreams(topicCountMap)
    val kafkaMessageStream = consumerMap.get(topic).get(0)
    println("After cconsumerMap.get==>")
    val consumerIterator = kafkaMessageStream.iterator()
    while(consumerIterator.hasNext()) {
      val msg = consumerIterator.next()
      println(new String(msg.message))
    }
    println("Succesfully completed")

  }

}
