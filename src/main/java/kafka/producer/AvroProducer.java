package kafka.producer;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.avro.Schema.Parser;
import java.io.File;
import java.time.Duration;
import java.util.*;
import java.io.File;
import java.io.IOException;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

@Slf4j
public class AvroProducer {


    public static void deliveryCallback(RecordMetadata msg) {
        // logs successful message infointeli
        log.info("Message delivered to \ntopic: " + msg.topic()
                + "\npartition: " + msg.partition()
                + "\noffset: " + msg.offset());
    }

    public static void deliveryCallback(Exception e) {
        // logs failed message exception
        log.info("Message failed to deliver: " + e.getMessage());
    }


    public static void producer(Map<String, String> configMap, GenericRecord message, String topic, String sr_url,String recKey) {
        if (configMap.containsKey("bootstrap.servers") && configMap.containsKey("group.id")) {
            Properties properties = new Properties();
            configMap.forEach(properties::setProperty);
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
            properties.put("schema.registry.url", sr_url);
      //      properties.put(AbstractKafkaAvroSerDeConfig.AUTO_REGISTER_SCHEMAS, true);

            KafkaProducer producer = new KafkaProducer(properties);

            ProducerRecord<Object, Object> record = new ProducerRecord<>(topic, recKey, message);
            try {
                producer.send(record, (recordMetadata, e) -> {
                    //executes when record is sent or exception is thrown
                    if (e == null) {
                        deliveryCallback(recordMetadata);
                    } else {
                        deliveryCallback(e);
                    }
                });
            } catch(
                    SerializationException e) {
            }
            finally {
                producer.flush();
                producer.close();
            }
        } else {
            throw new IllegalArgumentException("Config Map must contain at least 2 keys [bootstrap.servers, client.id]");
        }
    }
    public static void main(String[] args) {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("bootstrap.servers", "localhost:9092");
        testMap.put("group.id", "group1");
        testMap.put("client.id", "client1");
        String userSchema = "{\"type\":\"record\"," +
                "\"name\":\"myrecord\"," +
                "\"fields\":[{\"name\":\"f1\",\"type\":\"string\"}]}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);
        GenericRecord avroRecord = new GenericData.Record(schema);
        avroRecord.put("f1", "hello world today is 8/30");

       // String[] message1 = {"emma", "josh", "andy"};
        //List<String> nameList = Arrays.asList(message1);

        producer(testMap, avroRecord, "topic1",
                "http://localhost:8081", null);

    }

//    public static void main(String[] args) {
//        Schema schema = new Parser().parse(new File("/Users/hchen24/java-utils/src/main/java/kafka/schema/student.avsc"));
//        GenericRecord student1 = new GenericRecord.Record(schema);
//        student1.put("name", "Emily");
//        student1.put("major", "finance");
//
//        File file = new File("/Users/hchen24/java-utils/src/main/java/kafka/schema/student.avsc");
//
//        }
//    String schema = "{\n" +
//        "  \"type\": \"record\",\n" +
//        "  \"name\": \"students\",\n" +
//        "  \"fields\": [\n" +
//        "    {\n" +
//        "      \"name\": \"Name\",\n" +
//        "      \"type\": \"string\"\n" +
//        "    },\n" +
//        "    {\n" +
//        "      \"name\": \"major\",\n" +
//        "      \"type\": \"string\"\n" +
//        "    }\n" +
//        "  ]\n" +
//        "}"

}


