# Kafka Utils

Kafka utilities designed for reuse with different situations in mind. 


## Kafka Configuration

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

##Avro Configuration
Avro Configuration takes in a configuration map, schema registry URL, schema, and topic name.

###Avro Consumer
How to use the Avro Consumer:
1) Make a configuration Map. In the configuration map, declare a bootstrap server, client id, schema registry URL, and group id.

   Example configuration map:
``` java
            Map<String, String> configMap = new HashMap<>();
            configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configMap.put(ConsumerConfig.CLIENT_ID_CONFIG, "test");
            configMap.put("schema.registry.url", "http://localhost:8081");
            configMap.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
            
      Additional Notes: the above configuration map is an example, adjust the configurations according to your kafka topic.
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

