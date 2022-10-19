package kafka;
import beans.Students;
import kafka.consumer.CamelConsumer;
import kafka.producer.CamelProducer;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelTest {
    public static final Logger LOG = LoggerFactory.getLogger(CamelTest.class);
private final CamelProducer camelProducer;
private final ProducerTemplate producerTemplate;
    public CamelTest(CamelProducer camelProducer, ProducerTemplate producerTemplate) {
        this.camelProducer = camelProducer;
        this.producerTemplate = producerTemplate;
    }

    @Test
    public void testCamelConsumer() throws InterruptedException, IllegalAccessException {
        CamelConsumer.camelConsume();
    }

    @Test
    public void testCamelProducer() throws Exception {
//    CamelProducer.startCamelContext();
//       // CamelProducer.camelProduce();
//        CamelContext camelContext = new DefaultCamelContext();
//        Students testStudent = new Students();
//        testStudent.setMajor("biology");
//        testStudent.setName("Groot56837837878373788");
//        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
//        Endpoint ep = camelContext.getEndpoint("kafka:{{producer.topic}}?brokers={{kafka.brokers}}");
//        producerTemplate.setDefaultEndpoint(ep);
//        producerTemplate.sendBody(testStudent);
//        Thread.sleep(10L * 60 * 1000);
        camelProducer.createProducerTemplate();
        Students testStudent = new Students();
        testStudent.setMajor("biology");
        testStudent.setName("Groot56837837878373788");
        producerTemplate.sendBody(testStudent);
        Thread.sleep(10L * 60 * 1000);
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

