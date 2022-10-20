package kafka.producer;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public  class CamelProducer {
    public static final Logger LOG = LoggerFactory.getLogger(CamelProducer.class);
    public CamelProducer() throws IOException {}

    public static void setContext(CamelContext camelContext, String location) throws Exception {
         LOG.info("starting route:");
         camelContext.getPropertiesComponent().setLocation(location);
         camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:kafkaStart")
                            .to("kafka:{{producer.topic}}?brokers={{kafka.brokers}}&clientId={{producer.clientId}}&valueSerializer={{key-serializer-class}}&schemaRegistryURL={{schema.registry.url}}&specificAvroReader=true");
                }
            }));
            camelContext.start();
    }

    public static void setTemplate(ProducerTemplate producerTemplate, CamelContext camelContext, Object object) throws InterruptedException {
        producerTemplate = camelContext.createProducerTemplate();
        Endpoint ep = camelContext.getEndpoint("kafka:{{producer.topic}}?brokers={{kafka.brokers}}");
        producerTemplate.setDefaultEndpoint(ep);
        producerTemplate.sendBody(object);
        Thread.sleep(10L * 60 * 1000);
    }


}