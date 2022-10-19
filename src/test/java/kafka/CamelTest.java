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
    public void testCamelConsumer() throws InterruptedException, IllegalAccessException {
        CamelConsumer.camelConsume();
    }

    }

