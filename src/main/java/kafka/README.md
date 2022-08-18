# Kafka Utils

A variety of Kafka utilities have been designed for reuse with different situations in mind. See the list below to see the types of producers and consumers supported.

.
├──_producer/ # different Kafka producers
│  └── BasicProducer.java # basic
├──_consumer/ # different Kafka consumers
│  └── BasicConsumer.java # basic


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

### Example configuration map for consumer:

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

Automatic Batching based on inputted message type (list vs dict)
Kafka callbacks
