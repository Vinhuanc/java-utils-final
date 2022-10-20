package kafka;

import beans.Students;
import kafka.consumer.CamelConsumer;
import kafka.producer.CamelProducer;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelTest {
    public static final Logger LOG = LoggerFactory.getLogger(CamelTest.class);

    @Test
    public void testCamelProduce() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        CamelProducer.setContext(camelContext,"classpath:application.properties");

        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        Students testStudent = new Students();
        testStudent.setMajor("biology");
        testStudent.setName("10/20 10:33am");
        CamelProducer.setTemplate(producerTemplate, camelContext, testStudent);

    }
    @Test
    public void testCamelConsumer() throws InterruptedException, IllegalAccessException {
        CamelContext camelContext = new DefaultCamelContext();
        CamelConsumer.camelConsume(camelContext,"classpath:application.properties");
    }


}

