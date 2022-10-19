package kafka.producer;
import beans.Students;
import processor.*;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
public  class CamelProducer {
    public static final Logger LOG = LoggerFactory.getLogger(CamelProducer.class);
    public CamelProducer() throws IOException {  }
    public static void main(String[] args) throws Exception {

            CamelContext camelContext = new DefaultCamelContext();
            LOG.info("starting route:");
            camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
            camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:kafkaStart")
                            .process(new MapToGenericRecord())
                            .to("kafka:{{producer.topic}}?brokers={{kafka.brokers}}&clientId={{producer.clientId}}&valueSerializer={{value.serializer}}&schemaRegistryURL={{schema.registry.url}}&specificAvroReader=true");
                }
            }));
            camelContext.start();

            Students testStudent = new Students();
            testStudent.setMajor("biology");
            testStudent.setName("Groot568378dghmyfmdtm");
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            Endpoint ep = camelContext.getEndpoint("kafka:{{producer.topic}}?brokers={{kafka.brokers}}");
            producerTemplate.setDefaultEndpoint(ep);
            producerTemplate.sendBody(testStudent);
            Thread.sleep(10L * 60 * 1000);
        }


}