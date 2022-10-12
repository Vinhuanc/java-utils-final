package kafka.consumer;

import org.apache.camel.Configuration;
import org.apache.camel.component.kafka.serde.KafkaHeaderDeserializer;
import org.apache.camel.language.bean.Bean;

@Configuration
public class CamelKafkaConfiguration {

    @Bean(ref = "")
    public KafkaHeaderDeserializer kafkaHeaderDeserializerCustom(){

        return new bToStringHeaderDeserializer();

    }
}