package kafka.consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

@Slf4j

public class BasicConsumer {

    // Entrypoint consumer method. For basic consuming messages from Kafka
    public static void consumer(Map<String, String> configMap, String topic) {
        //set properties from configMap
        if (configMap.containsKey("bootstrap.servers") && configMap.containsKey("group.id")) {
            Properties properties = new Properties();
            configMap.forEach(properties::setProperty);

            //create consumer
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

            //get reference to current thread
            final Thread mainThread = Thread.currentThread();

            //shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("Shutdown started... calling consumer.wakeup()...");
                consumer.wakeup();

                //join the main thread to allow execution of code in main thread
                try {
                    mainThread.join();

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }));

            try {
                //subscribe consumer to topic
                consumer.subscribe(Collections.singleton(topic));

                //poll for new data
                while (true) {
                    log.info("Consumer polling for Topic: " + topic + "...");
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(15000));

                    for (ConsumerRecord<String, String> msg : records) {
                        log.info("Message from Partition: " + msg.partition() + ". Message from Offset: " + msg.offset());
                        log.info("Message Key: " + msg.key() + ", Message Key Value: " + msg.value());
                    }
                }
            } catch (WakeupException e) {
                log.info("Wake up exception");
            } catch (Exception e) {
                log.error("Unexpected exception");
            } finally {
                consumer.close(); //commit offsets if necessary
                log.info("Consumer closed");
            }


        } else {
            throw new IllegalArgumentException("Config Map must contain at least 2 keys [bootstrap.servers, group.id]");
        }
    }

    public BasicConsumer() {
    }
}
