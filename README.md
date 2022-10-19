# Harvester Java Utils

This repository contains a variety of reuseable utility functions that can be installed and utlized in different projects as a module. It contains utility functions for Basic, Avro,and Camel Producers and Consumers.

## Install instructions: 
``` java
git clone https://github.optum.com/HaaS-NonProd/java-utils.git
```
## Pre-requisites
In order to use the utility functions in the repository, zookeeper and kafka must be running, it can be done through Docker or Terminal. Setting up schema registry is not needed for the Basic producer and consumer, but schema registry setup is needed to use Avro and Camel producer and consumer. <br>
 * **Docker**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1) Clone the OCG's local kafka development file
 ``` java
    git clone https://github.optum.com/ClinicalGateway/local-kafka-dev-env.git
``` 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2) Follow through local-kafka-dev-env READMe.md page to connect with Docker

* **Terminal**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1) Download and unzip the kafka_2.13-3.3.1.tgz from Binary downloads Scala 2.13

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2) Open new terminal, drag the zookeeper-server-start.sh file from kafka_2.13-3.3.1 file into terminal and push enter. This will start Zookeeper.
 Example:
``` java
 MSID@LAMU0XTVYVL40F3 ~ % /Users/MSID/Downloads/kafka_2.13-3.2.1/bin/zookeeper-server-start.sh 
 ```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3) Open another new terminal, drag the kafka-server-start.sh file from kafka_2.13-3.3.1 file into terminal and push enter. This will start kafka server. Example:
``` java
 MSID@LAMU0XTVYVL40F3 ~ %  /Users/MSID/Downloads/kafka_2.13-3.2.1/bin/kafka-server-start.sh 
 ```
* **Create Topic**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1) Download and unzip the kafka_2.13-3.3.1.tgz from Binary downloads Scala 2.13


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2) Open new terminal, drag the zookeeper-server-start.sh file from kafka_2.13-3.3.1 file into terminal and push enter. This will start Zookeeper.
Example:
``` java
 MSID@LAMU0XTVYVL40F3 ~ % /Users/MSID/Downloads/kafka_2.13-3.2.1/bin/zookeeper-server-start.sh 
 ```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3) Open another new terminal, drag the kafka-server-start.sh file from kafka_2.13-3.3.1 file into terminal and push enter. This will start kafka server. Example:
``` java
 MSID@LAMU0XTVYVL40F3 ~ %  /Users/MSID/Downloads/kafka_2.13-3.2.1/bin/kafka-server-start.sh 
 ```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 4) Open another new terminal, drag the kafka-topics.sh file from kafka_2.13-3.3.1 file into terminal, create a topic by following syntax: --create --topic yourTopicName --replication-factor # --partitions # --bootstrap-server yourbootstrapServer. This will create a topic. Example:
``` java
MSID@LAMU0XTVYVL40F3 ~ % /Users/MSID/Downloads/kafka_2.13-3.2.1/bin/kafka-topics.sh --create --topic topic123 --replication-factor 1 --partitions 1 --bootstrap-server localhost:9092
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Explanation: the above code creates a new topic with the name topic123, with a replication-factor of 1, with partition factor of 1, and configures the bootstrap server to localhost:9092.


* **Schema Registry Setup**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1) Download the Confluent file
``` java
curl -O http://packages.confluent.io/archive/7.2/confluent-7.2.2.zip
``` 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2) Unzip the confluent file
``` java
unzip confluent-7.2.2.zip
``` 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3) Open new terminal, drag the zookeeper-server-start file from confluent-7.2.2 file into terminal and push enter. This will start Zookeeper. Example:
``` java
MSID@LAMU0XTVYVL40F3 ~ % /Users/MSID/Downloads/confluent-7.2.2/bin/zookeeper-server-start
``` 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 4) Open another new terminal, drag the kafka-server-start file from confluent-7.2.2 file into terminal and push enter. This will start kafka server. Example:
``` java
MSID@LAMU0XTVYVL40F3 ~ % /Users/MSID/Downloads/confluent-7.2.2/bin/kafka-server-start 
``` 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5) Open new terminal, drag the schema-registry-start file from confluent-7.2.2 file into terminal and push enter. This will start the schema registry server. Example:
``` java
MSID@LAMU0XTVYVL40F3 ~ % /Users/MSID/Downloads/confluent-7.2.1/bin/schema-registry-start 
``` 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 6) Using the format.py from [here](https://github.optum.com/HaaS/avro_schemas),schema will be formated into confluent's format

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 7) Open new terminal, upload schema into the schema registry. Upload schema into schema registry by following syntax:   curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
--data '{"schema": "pasteSchemaHere"}' \ http://bootstrap-server/subjects/yourTopicName-value/versions Example:
``` java
MSID@LAMU0XTVYVL40F3 ~ % curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
                                                                   --data '{"schema": "{\"type\": \"record\", \"name\": \"school.students\", \"fields\": [{\"name\": \"Name\", \"type\": \"string\"}, {\"name\": \"major\", \"type\": \"string\"}]}"}' \
                                                                   http://localhost:8081/subjects/camelTopicTest-value/versions
```
Explanation: the above code uploads the student schema into http://localhost:8081 with the topic name camelTopicTest

# Kafka Utils
Kafka utilities designed for reuse with different situations in mind.


## Basic Configuration

For every utility the first parameter is configMap. The config map can contain any key/value pair that is expected for the producer and consumer config properties.


### Example configuration map for consumer:

```
        //collection of config properties
        Map<String, String> kafkaConfig = new HashMap<>();
        //set properties
        kafkaConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        kafkaConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-test");
        kafkaConfig.put(ConsumerConfig.CLIENT_ID_CONFIG, "test");
        kafkaConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
```

### Example configuration map for producer:
~~~~~~~~
```
        //collection of config properties
        Map<String, String> kafkaConfig = new HashMap<>();
        //set properties
        kafkaConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        kafkaConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "test");
        kafkaConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
```

## Utility Feature List

Automatic Batching based on inputted message type

Kafka callbacks
~~~~~~~~


## Avro Configuration
Avro Configuration takes in a configuration map, schema registry URL, schema, and topic name.

### Avro Consumer
How to use the Avro Consumer:
1) Make a configuration Map. In the configuration map, declare a bootstrap server, client id, schema registry URL, and group id.

   Example configuration map:
``` java
=======
<h3> How to use Avro Consumer </h3>

The avroConsumer method takes in a configuration map, topic name, and schema URL. To use the schema method, do this: <br>
1. <strong>Make a configuration map. Within the configuration map, declare a bootstrap server, client id, schema registry URl, and group id.</strong> <br>
   example:  <br>
>>>>>>> f6a98ab74a703d223c84c21e3dfafa6788337ee8
            Map<String, String> configMap = new HashMap<>();
            configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configMap.put(ConsumerConfig.CLIENT_ID_CONFIG, "test");
            configMap.put("schema.registry.url", "http://localhost:8081");
            configMap.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
            
<<<<<<< HEAD
    //  Additional Notes: the above configuration map is an example, adjust the configurations according to your kafka topic.
```

2) Create the avroConsumer class as an object.

``` java
     AvroConsumer reusableAvroConsumer = new AvroConsumer();
```
3) Call the avroConsumer method. Insert your configuration map, topic name, and schema registry URL for parameters.

   Example:
``` java
reusableAvroConsumer.consume(configMap, "topicTest","http://localhost:8081");
```


## Camel Configuration

### Camel Consumer:
How to use the Camel Consumer
1. Configure the Applications.Properties file. Change properties to accommodate your topic. 
     <br>
            Note: Please see example in src/main/resources/application.properties
``` java
kafka.brokers= //input kafka broker 
schema.registry.url = //input schema registry URL 

# producer properties
producer.topic= //input topic name
key-serializer-class: org.apache.kafka.common.serialization.StringSerializer
value.serializer: io.confluent.kafka.serializers.KafkaAvroSerializer
producer.clientId: //input client id

# Consumer properties
consumer.topic= //input topic name
consumer.group= //input group name
consumer.maxPollRecords=5000
consumer.consumersCount=1
consumer.seekTo=end
key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
value.deserializer: io.confluent.kafka.serializers.KafkaAvroDeserializer
```
2. Call the CamelConsumer.camelConsume() method within your consumer class. 
<br>
Note: calling the method will throw an error, click on "add exception to method signature". A InterruptedException and IllegalAccessException will be imported.

``` java
public static void main(String [] args) throws InterruptedException, IllegalAccessException {
        CamelConsumer.camelConsume();
    }
```