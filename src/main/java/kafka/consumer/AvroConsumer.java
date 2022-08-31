package kafka.consumer;

import com.nimbusds.jose.shaded.json.parser.JSONParser;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.FileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.kafka.clients.consumer.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class AvroConsumer {

    public static void consumer(Map<String, String> configMap, String topic, String schema_path, String schema_url) throws IOException {
        if (configMap.containsKey("bootstrap.servers") && configMap.containsKey("client.id")) {
            Properties properties = new Properties();
            configMap.forEach(properties::setProperty);
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
            properties.put("schema.registry.url", schema_url);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");

        if(schema_path != null){
            Schema schema = new Schema.Parser().parse(new File(schema_path));
            DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
            DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(schema_path), datumReader);
            dataFileReader.getSchema();

        } else {

        }
        //    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
         //   properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

            final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(List.of(topic));
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

//*****************Nick's repo*************************

//import lombok.extern.slf4j.Slf4j;
//import org.apache.avro.generic.GenericRecord;
//import org.apache.kafka.clients.consumer.*;
//import java.time.Duration;
//import java.util.*;
//
//@Slf4j
//
//public class AvroConsumer {
//    public static void consumer(Map<String, String> configMap, String topic, String srURL) {
//
//        if (configMap.containsKey("bootstrap.servers") && configMap.containsKey("client.id")) {
//            final Properties properties = new Properties();
//            configMap.forEach(properties::setProperty);
//            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
//            properties.put("schema.registry.url", srURL);
//            final Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
//            consumer.subscribe(List.of(topic));
//
//            try {
//                while (true) {
//                    log.info("Consumer polling for Topic: " + topic + "...");
//                    ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(100));
//                    for (ConsumerRecord<String, GenericRecord> record : records) {
//                        System.out.printf("offset = %d, key = %s, value = %s %n", record.offset(), record.key(), record.value());
//                    }
//                }
//            } finally {
//                consumer.close();
//            }
//
//        } else {
//            throw new IllegalArgumentException("Config Map must contain at least 2 keys [bootstrap.servers, client.id]");
//        }
//    }
//
//    public static void main(final String[] args) {
//        HashMap<String, String> props = new HashMap<>();
//
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "test");
//        consumer(props, "avro-test", "http://localhost:8081");
//
//    }
//}

