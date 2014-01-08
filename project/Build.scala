import sbt._


import sbtassembly.Plugin._
import AssemblyKeys._

import com.github.retronym.SbtOneJar
import com.github.retronym.SbtOneJar.oneJar

import Keys._

object KafkaConsumerBuild extends Build {
	
	val kafka = "org.apache.kafka" %% "kafka" % "0.8.0" exclude("com.sun.jdmk", "jmxtools") exclude("com.sun.jmx", "jmxri")
	//val yammer = "com.yammer.metrics" % "metrics-core" % yammerVersion
	val log4j = "log4j" % "log4j" % "1.2.12"
	val zookeeper = "org.apache.zookeeper" % "zookeeper" % "3.3.1" intransitive() // don't add any any extra dependencies

	/*val projSettings = assemblySettings ++ Seq(
	  scalaVersion := "2.10.2",
	  mainClass in assembly := Some("vj.kafka.test.Runner"),
	  jarName in assembly := "kafka-consumer.jar",
	  target in assembly  <<= (baseDirectory) { new File(_, "dist") },
	  excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
	  	cp filter { jar => jar.data.getName == "kafka-0.7.0-incubating.jar"}
	  }
	)*/

	val projSettings = SbtOneJar.oneJarSettings ++ Seq(
	  scalaVersion := "2.10.2",
	  mainClass in oneJar := Some("vj.kafka.test.Runner")
	  /*excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
	  	cp filter { jar => jar.data.getName == "kafka-0.7.0-incubating.jar"}
	  }*/
	)

	lazy val project = Project(
	  id = "kakfa-consumer",
	  base = file("."),
	  settings = Project.defaultSettings ++ projSettings ++ Seq(
	  	libraryDependencies ++= Seq(kafka),
	  	resolvers += "clojars" at "http://clojars.org/repo"
	  )
	)
}

