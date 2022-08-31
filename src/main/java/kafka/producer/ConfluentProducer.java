package kafka.producer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;

import java.io.File;
import java.util.Properties;

public class ConfluentProducer {
    public static void main(final String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://localhost:8081");
        KafkaProducer producer2 = new KafkaProducer(props);

        String key = "key1";
//        String userSchema = "{\"type\":\"record\"," +
//                "\"name\":\"myrecord\"," +
//                "\"fields\":[{\"name\":\"f1\",\"type\":\"string\"}]}";

        String userSchema = " {\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"students\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"Name\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"major\",\n" +
                "      \"type\": \"string\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);
        GenericRecord avroRecord = new GenericData.Record(schema);
      //  avroRecord.put("f1", "value1");
        avroRecord.put("Name", "Eli");
        avroRecord.put("major", "Aerospace Engineering");


        ProducerRecord<Object, Object> record = new ProducerRecord<>("StudentTopic", key, avroRecord);
        try {
            producer2.send(record);
        } catch (
                SerializationException e) {
            e.printStackTrace();
        }
// When you're finished producing records, you can flush the producer to ensure it has all been written to Kafka and
// then close the producer to free its resources.
        finally {
            producer2.flush();
            producer2.close();
        }
    }
}
