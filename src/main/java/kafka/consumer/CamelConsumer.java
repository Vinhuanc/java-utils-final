package kafka.consumer;
import io.confluent.common.config.ConfigException;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

//import java.util.Collections;
//import java.util.List;

public class CamelConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(CamelConsumer.class);
    private static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";
    private CamelConsumer() {
    }
    public static void setUpKafkaComponent(CamelContext camelContext) {
        // setup kafka component with the brokers
        ComponentsBuilderFactory.kafka()
                .brokers("{{kafka.brokers}}")
                .register(camelContext, "kafka");
    }
    public static void main(String[] args) throws Exception {

        LOG.info("About to run Kafka-camel integration...");

//        try {
//
//            final List<String> schemas = Collections.singletonList(SCHEMA_REGISTRY_URL);
//            this.schemaRegistry = new CachedSchemaRegistryClient(schemas, Integer.MAX_VALUE);
//            this.useSpecificAvroReader = true;
//
//        } catch (ConfigException e) {
//            e.printStackTrace();
//            throw new org.apache.kafka.common.config.ConfigException(e.getMessage());
//        }
  //  public static void startCamelConsumer(){
        CamelContext camelContext = new DefaultCamelContext();

        LOG.info("starting route:");
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
        setUpKafkaComponent(camelContext);
        camelContext.addRoutes(createRouteBuilder());
        camelContext.start();

        Thread.sleep(9L * 60 * 1000);
        }
  //  }
    public static RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("kafka:{{consumer.topic}}"
                        + "?maxPollRecords={{consumer.maxPollRecords}}"
                        + "&consumersCount={{consumer.consumersCount}}"
                        + "&seekTo={{consumer.seekTo}}"
                        + "&groupId={{consumer.group}}"
                )
                        .routeId("FromKafka")
                        .log("${body}");
            }
        };
    }
}
