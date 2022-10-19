package kafka.producer;
import beans.Students;
import processor.*;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public  class CamelProducer {
    public static final Logger LOG = LoggerFactory.getLogger(CamelProducer.class);
private final CamelContext camelContext ;
private final ProducerTemplate producerTemplate;

    public CamelProducer(CamelContext camelContext, ProducerTemplate producerTemplate) throws IOException {
        this.camelContext = camelContext;
        this.producerTemplate = producerTemplate;
    }

    // public static void main(String[] args) throws Exception {
    public  CamelContext startCamelContext() throws Exception {
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
//        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
//        Endpoint ep = camelContext.getEndpoint("kafka:{{producer.topic}}?brokers={{kafka.brokers}}");
//        producerTemplate.setDefaultEndpoint(ep);

        return camelContext;
    }

    public ProducerTemplate createProducerTemplate() throws Exception {
        startCamelContext();
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        Endpoint ep2 = camelContext.getEndpoint("kafka:{{producer.topic}}?brokers={{kafka.brokers}}");
        producerTemplate.setDefaultEndpoint(ep2);
        return producerTemplate;

    }

//            producerTemplate.sendBody(testStudent);
//            Thread.sleep(10L*60*1000);

//    public void main(String[] args) throws Exception {
//    //    public void camelProduce () throws Exception {
//            createProducerTemplate();
//            Students testStudent = new Students();
//            testStudent.setMajor("biology");
//            testStudent.setName("Groot56837837878373788");
//            producerTemplate.sendBody(testStudent);
//            Thread.sleep(10L * 60 * 1000);
//     //   }
//    }
            //fetch camelCOntext //get the camelcontext
//            Students testStudent = new Students();
//            testStudent.setMajor("biology");
//            testStudent.setName("Groot56837837878373788");
//            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
//            Endpoint ep = camelContext.getEndpoint("kafka:{{producer.topic}}?brokers={{kafka.brokers}}");
//            producerTemplate.setDefaultEndpoint(ep);
//            producerTemplate.sendBody(testStudent);
//            Thread.sleep(10L * 60 * 1000);

  //  }
}
