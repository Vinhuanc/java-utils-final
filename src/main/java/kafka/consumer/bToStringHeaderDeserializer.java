package kafka.consumer;

import org.apache.camel.component.kafka.serde.KafkaHeaderDeserializer;

import java.nio.charset.StandardCharsets;

public class bToStringHeaderDeserializer implements KafkaHeaderDeserializer {

    @Override
    public Object deserialize(String key, byte[] value) {
        return new String(value, StandardCharsets.UTF_8);
    }
}
