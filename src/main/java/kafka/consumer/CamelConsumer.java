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

    public static void startCamel(String fromURI, String routeId) {
        CamelContext camelContext = new DefaultCamelContext();
        LOG.info("starting route:");
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
        setUpKafkaComponent(camelContext);
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    if(fromURI.isEmpty() && routeId.isEmpty()){
                        from("kafka:{{consumer.topic}}\"\n" +
                                "                + \"?maxPollRecords={{consumer.maxPollRecords}}\"\n" +
                                "                + \"&consumersCount={{consumer.consumersCount}}\"\n" +
                                "                + \"&seekTo={{consumer.seekTo}}\"\n" +
                                "                + \"&groupId={{consumer.group}}")
                                .routeId("FromKafka")
                                .log("${body}");
                    }else {
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
