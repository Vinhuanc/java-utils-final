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
    public CamelConsumer() { }

    public static void camelConsume() throws InterruptedException, IllegalAccessException {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
        try {
            camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("kafka:{{consumer.topic}}?brokers={{kafka.brokers}}&groupId={{consumer.group}}&valueSerializer={{value.serializer}}&schemaRegistryURL={{schema.registry.url}}&specificAvroReader=true&maxPollRecords={{consumer.maxPollRecords}}")
                            .log("Polling.....")
                            .process(new Processor() {
                                @Override
                                public void process(Exchange exchange) throws Exception {
                                    String body = exchange.getIn().getBody(String.class);
                                    System.out.println("<Message from kafka:>" + body);
                                }
                            })
                            .log("${body}");
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        camelContext.start();
        ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
        String consumedMessage = consumerTemplate.receiveBody("kafka:{{consumer.topic}}?brokers={{kafka.brokers}}", String.class);
        System.out.println(consumedMessage);
        Thread.sleep(10L * 60 * 1000);
        }
}