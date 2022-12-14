package kafka;

import kafka.consumer.AvroConsumer;
import kafka.producer.AvroProducer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class AvroTest {
     @Test
     public void testProducer() {
            Map<String, String> configMap = new HashMap<>();
            configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configMap.put(ProducerConfig.CLIENT_ID_CONFIG, "test");
            configMap.put("schema.registry.url", "http://localhost:8081");

            AvroProducer reusableAvroProducer = new AvroProducer();

         String userSchema =" {\n" +
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
            avroRecord.put("Name", "Sydney");
            avroRecord.put("major", "business administration");

         reusableAvroProducer.produce(configMap, "topicTest", "key1", avroRecord, "http://localhost:8081");

        }
        @Test
                public void testConsumer(){
            Map<String, String> configMap = new HashMap<>();
            configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configMap.put(ConsumerConfig.CLIENT_ID_CONFIG, "test");
            configMap.put("schema.registry.url", "http://localhost:8081");
            configMap.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");

            AvroConsumer reusableAvroConsumer = new AvroConsumer();
            reusableAvroConsumer.consume(configMap, "topicTest","http://localhost:8081");
        }


    }

