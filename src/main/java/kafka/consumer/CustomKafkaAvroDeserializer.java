package kafka.consumer;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.serializers.AbstractKafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

//public class CustomKafkaAvroDeserializer extends AbstractKafkaAvroDeserializer
//        implements Deserializer<Object> {
//    private static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";
//
//    @Override
//    public void configure(KafkaAvroDeserializerConfig config) {
//
//        try {
//            final List<String> schemas =
//                    Collections.singletonList(SCHEMA_REGISTRY_URL);
//            this.schemaRegistry = new CachedSchemaRegistryClient(schemas,
//                    Integer.MAX_VALUE);
//            this.useSpecificAvroReader = true;
//
//        } catch (ConfigException e) {
//            throw new org.apache.kafka.common.config.ConfigException(e.getMessage());
//        }
//    }
//
//    @Override
//    public void configure(Map<String, ?> configs, boolean isKey) {
//        configure(null);
//    }
//
//    @Override
//    public Object deserialize(String s, byte[] bytes) {
//        return deserialize(bytes);
//    }
//
//    @Override
//    public void close() {
//    }
//}
