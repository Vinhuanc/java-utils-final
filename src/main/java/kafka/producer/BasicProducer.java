package kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j

public class BasicProducer {

    // Method Kafka uses to return after a message attempts to publish to topic
    public static void deliveryCallback(RecordMetadata msg) {
        // logs successful message info
        log.info("Message delivered to \ntopic: " + msg.topic()
                + "\npartition: " + msg.partition()
                + "\noffset: " + msg.offset());
    }

    public static void deliveryCallback(Exception e) {
        // logs failed message exception
        log.info("Message failed to deliver: " + e.getMessage());
    }

    //Entrypoint producer method. For basic pushing messages to Kafka.
    public static void producer(Map<String, String> configMap, String message, String topic, String recKey) {
        //set properties from configMap
        //can only have items specified here: https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html
        if (configMap.containsKey("bootstrap.servers") && configMap.containsKey("client.id")) {
            Properties properties = new Properties();
            configMap.forEach(properties::setProperty);

            //create the producer
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

            //create a producer record
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, recKey, message);

            producer.send(producerRecord, (recordMetadata, e) -> {
                //executes when record is sent or exception is thrown
                if (e == null) {
                    deliveryCallback(recordMetadata);
                } else {
                    deliveryCallback(e);
                }
            });
            producer.close();
        } else {
            throw new IllegalArgumentException("Config Map must contain at least 2 keys [bootstrap.servers, client.id]");
        }
    }

    // producer method for batch
    public static void producer(Map<String, String> configMap, List<String> messages, String topic, String recKey) {
        //set properties from configMap
        //can only have items specified here: https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html
        if (configMap.containsKey("bootstrap.servers") && configMap.containsKey("client.id")) {
            Properties properties = new Properties();
            configMap.forEach(properties::setProperty);

            //create the producer
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

            //create a producer record
            for (String message : messages) {

                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, recKey, message);

                producer.send(producerRecord, (recordMetadata, e) -> {
                    //executes when record is sent or exception is thrown
                    if (e == null) {
                        deliveryCallback(recordMetadata);
                    } else {
                        deliveryCallback(e);
                    }
                });
            }
            producer.close();

        } else {
            throw new IllegalArgumentException("Config Map must contain at least 2 keys [bootstrap.servers, client.id]");
        }
    }

    private BasicProducer() {
    }
}
