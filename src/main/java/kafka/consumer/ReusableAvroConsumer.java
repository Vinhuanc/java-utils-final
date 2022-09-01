package kafka.consumer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class ReusableAvroConsumer {
    public static void consume(Map<String, String> configMap, String topic, String schema_url) {
        if (configMap.containsKey("bootstrap.servers") && configMap.containsKey("client.id")) {
            Properties properties = new Properties();
            configMap.forEach(properties::setProperty);
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
            properties.put("schema.registry.url", schema_url);
            properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group5");

            final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(Arrays.asList(topic));

            try {
                while (true) {
                    ConsumerRecords<String, GenericRecord> records = consumer.poll(100);
                    for (ConsumerRecord<String, GenericRecord> record : records) {
                        System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
                    }
                }
            } finally {
                consumer.close();
            }

        }

    }
}
