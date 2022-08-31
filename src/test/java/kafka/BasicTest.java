package kafka;

import kafka.producer.BasicProducer;
//import kafka.consumer.BasicConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j

public class BasicTest {

    @Test
    public void runProducer() {
        //collection of config properties
        Map<String, String> kafkaConfig = new HashMap<>();
        //set properties
        kafkaConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        kafkaConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "test");
        kafkaConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        for (int i = 0; i < 10; i++) {
            String topic = "test-topic";
            String value = "test message " + i;
            String key = "id_" + i;
            BasicProducer.producer(kafkaConfig, value, topic, key);

        }


    }


    @Test
    public void produceBatch() {
        //collection of config properties
        Map<String, String> kafkaConfig = new HashMap<>();
        //set properties
        kafkaConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        kafkaConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "test");
        kafkaConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        List<String> messages = new ArrayList<>();
        String topic = "test-topic";

        String key = "id_69";

        for (int i = 0; i < 10; i++) {
            messages.add("batch message number " + i);
        }

        BasicProducer.producer(kafkaConfig, messages, topic, key);
    }

    @Test
    public void runConsumer(){
        //collection of config properties
        Map<String, String> kafkaConfig = new HashMap<>();
        //set properties
        kafkaConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        kafkaConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-test");
        kafkaConfig.put(ConsumerConfig.CLIENT_ID_CONFIG, "test");
        kafkaConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        String topic = "test-topic";

    //    BasicConsumer.consumer(kafkaConfig, topic);
    }

}
