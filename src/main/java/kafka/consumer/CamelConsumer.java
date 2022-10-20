package kafka.consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.impl.DefaultCamelContext;
import processor.*;
public final class CamelConsumer {
    public static final Logger LOG = LoggerFactory.getLogger(CamelConsumer.class);
    public CamelConsumer() {}

    public static void camelConsume(CamelContext camelContext, String location) throws InterruptedException, IllegalAccessException {
        camelContext.getPropertiesComponent().setLocation(location);
        try {
            camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                from("kafka:{{consumer.topic}}?brokers={{kafka.brokers}}&groupId={{consumer.group}}&valueDeserializer={{key-deserializer}}&schemaRegistryURL={{schema.registry.url}}&specificAvroReader=true&maxPollRecords={{consumer.maxPollRecords}}")
                    .log("Polling.....")
                            .log("${body}");
             }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        camelContext.start();
        Thread.sleep(10L * 60 * 1000);
        }
}