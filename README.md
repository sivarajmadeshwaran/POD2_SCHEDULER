# POD2_SCHEDULER

## APP Objective:
      BackEnd services is developed for the below domains,
      1. DC,DCSlot,Vendor,Truck  domain related CRUD Operations
      2. Appointment domain related CRUD Operations & Asychronous Message Events
      3. Purchase Order domain related CRUD Operations & Asychronous Message Events
## MicroService Design
  ![Alt Text](https://github.com/sivarajmadeshwaran/POD2_SCHEDULER/blob/main/CaseStudy_Docs/Atlas_Microservices_Design.PNG)
 
## Data Model Design
   ![Atlas TruckBooking App Data Model](https://github.com/sivarajmadeshwaran/POD2_SCHEDULER/blob/main/CaseStudy_Docs/Atlas_Data_Model.pdf)
   

## Kafka Setup for Appointment & Third Party Application Interaction

Before launching appointment service need to configure Kafka:

  We have to execute the following commtents in command prompt in windows os inside bin/windows folder (i.e : c:/Desktop/kafka/bin/windows)

      1.zookeeper-server-start.bat ..\..\config\zookeeper.properties
      2.kafka-server-start.bat ..\..\config\server.properties
      3. kafka-topics.bat --create --topic appointment-kafka -zookeeper localhost:2181 --replication-factor 1 --partitions 4(if already created skip this step)
      4. kafka-console-producer.bat --broker-list localhost:9092 --topic appointment-kafka
      5. kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic appointment-kafka --from-beginning

## Kafka Setup for PurchaseOrder & Third party Application Interaction
     1.zookeeper-server-start.bat ..\..\config\zookeeper.properties
     2.kafka-server-start.bat ..\..\config\server.properties
     3.kafka-topics.bat --create --topic po_download -zookeeper localhost:2181 --replication-factor 2 --partitions 3(if already created skip this step)
     4.kafka-topics.bat --create --topic po_download_retry -zookeeper localhost:2181 --replication-factor 2 --partitions 3(if already created skip this step)
     
  
