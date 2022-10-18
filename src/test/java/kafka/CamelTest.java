package kafka;

import beans.Students;
import kafka.consumer.CamelConsumer;
import kafka.producer.CamelProducer;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.util.concurrent.NamedThreadLocal;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kafka.consumer.CamelConsumer.*;
import static kafka.consumer.CamelConsumer.*;
public class CamelTest {
    public static final Logger LOG = LoggerFactory.getLogger(CamelTest.class);

    @Test
    public void testCamelConsumer() throws InterruptedException, IllegalAccessException {
        CamelConsumer.camelConsume();
    }

    @Test
    public void testCamelProducer() throws Exception {

        CamelProducer.camelProduce();
//        CamelContext camelContext = new DefaultCamelContext();
//        Students testStudent= new Students();
//        testStudent.setMajor("biology");
//        testStudent.setName("Groot56837837878373788");
//        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
//        Endpoint ep = camelContext.getEndpoint("kafka:{{producer.topic}}?brokers={{kafka.brokers}}");
//        producerTemplate.setDefaultEndpoint(ep);
//        producerTemplate.sendBody(testStudent);
    }
    }
