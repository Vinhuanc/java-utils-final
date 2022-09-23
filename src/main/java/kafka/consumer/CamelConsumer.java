package kafka.consumer;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.camel.spi.*;
import org.apache.camel.util.concurrent.NamedThreadLocal;
public final class CamelConsumer {
    public static final Logger LOG = LoggerFactory.getLogger(CamelConsumer.class);
    public CamelConsumer(){}

    public static void setUpKafkaComponent(CamelContext camelContext) {
        // setup kafka component with the brokers
        ComponentsBuilderFactory.kafka()
                .brokers("{{kafka.brokers}}")
                .register(camelContext, "kafka");
    }

    public static RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {


                    from("kafka:{{consumer.topic}}"
                            + "?maxPollRecords={{consumer.maxPollRecords}}"
                            + "&consumersCount={{consumer.consumersCount}}"
                            + "&seekTo={{consumer.seekTo}}"
                            + "&groupId={{consumer.group}}"
                    )



//                from("kafka:test?brokers=localhost:9092")
//                                .log("Message received from Kafka : ${body}")
//                                .log("    on the topic ${headers[kafka.TOPIC]}")
//                                .log("    on the partition ${headers[kafka.PARTITION]}")
//                                .log("    with the offset ${headers[kafka.OFFSET]}")
//                                .log("    with the key ${headers[kafka.KEY]}")
                                .routeId("FromKafka")
                        .log("${body}");
            }
        };
    }
    public static void main(String[]args) throws Exception {
   // public static void startCamel(){
        CamelContext camelContext = new DefaultCamelContext();
        LOG.info("starting route:");
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
        setUpKafkaComponent(camelContext);
        camelContext.addRoutes(createRouteBuilder());
        camelContext.start();
        Thread.sleep(5L * 60 * 1000);
   // }





    }

}
