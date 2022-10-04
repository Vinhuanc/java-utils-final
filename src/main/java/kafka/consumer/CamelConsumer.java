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

    //default camel consumer
    public static void camelDefaultReceive() {
        CamelContext camelContext = new DefaultCamelContext();
        LOG.info("starting route:");
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
        setUpKafkaComponent(camelContext);
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("kafka:{{consumer.topic}}"
                            + "?maxPollRecords={{consumer.maxPollRecords}}"
                            + "&consumersCount={{consumer.consumersCount}}"
                            + "&seekTo={{consumer.seekTo}}"
                            + "&groupId={{consumer.group}}")
                            .routeId("FromKafka")
                            .log("${body}");

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        camelContext.start();
        try {
            Thread.sleep(90000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void camelReceive( String fromURI, String routeId) {
        CamelContext camelContext = new DefaultCamelContext();
        LOG.info("starting route:");
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
        setUpKafkaComponent(camelContext);
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("kafka:{{consumer.topic}}"
                            + "?maxPollRecords={{consumer.maxPollRecords}}"
                            + "&consumersCount={{consumer.consumersCount}}"
                            + "&seekTo={{consumer.seekTo}}"
                            + "&groupId={{consumer.group}}")
                            .routeId("FromKafka")
                            .log("${body}");
                    if(fromURI != null && routeId != null){
                        from(fromURI)
                                .routeId(routeId)
                                .log("${body}");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        camelContext.start();
        try {
            Thread.sleep(90000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
