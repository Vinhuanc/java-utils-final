package processor;




import beans.Students;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KafkaAvroMessageConsumerProcessor implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaAvroMessageConsumerProcessor.class);
//private test
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        Object body2 = exchange.getIn().getBody();
        System.out.print(body2.getClass().getDeclaredFields().toString());
        System.out.print("KafkaAvroMessageConsumerProcessor:" + body);
    }
}
