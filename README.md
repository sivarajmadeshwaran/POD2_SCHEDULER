# POD2_SCHEDULER

Before launching appointment service need to configure Kafka:

  We have to execute the following commtents in command prompt in windows os inside bin/windows folder (i.e : c:/Desktop/kafka/bin/windows)

      1.zookeeper-server-start.bat ..\..\config\zookeeper.properties

      2.kafka-server-start.bat ..\..\config\server.properties

      3. kafka-topics.bat --create --topic appointment-kafka -zookeeper localhost:2181 --replication-factor 1 --partitions 4(if already created skip this step)

      4. kafka-console-producer.bat --broker-list localhost:9092 --topic appointment-kafka

      5. kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic appointment-kafka --from-beginning
