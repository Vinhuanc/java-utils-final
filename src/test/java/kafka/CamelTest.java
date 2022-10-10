package kafka;

import kafka.consumer.CamelConsumer;
import kafka.producer.CamelProducer;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
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
public class CamelTest  {
    public static final Logger LOG = LoggerFactory.getLogger(CamelTest.class);

//    @Test
//            public void testCamelConsumer() {
        //If the programmer wants to use the default fromURI and routeId, do this.
       // camelReceive(null, null);
     //   CamelConsumer.camelDefaultReceive();

        //If the programmer has different fromURI and routId, do this.
        //CamelConsumer.startCamel("insertYourFromURIhere", "insertYourRouteIdhere");




  //  }
public static void main(String[]args){
   // CamelConsumer.camelReceive("direct:kafkaStart","FirectToKafka" );
 //  CamelProducer.testSendToSpecificUri();
    CamelContext camel = new DefaultCamelContext();
    camel.start();
    ConsumerTemplate consumer = camel.createConsumerTemplate();
    String message= consumer.receiveBody("direct:basic", String.class);
    System.out.print(message);

 //  CamelConsumer.camelCustomeReceive("direct:out");
}

//this one works?! result: Subscribing topicTest
//    @Test
//    public void testcamelDefaultReceive(){
//        CamelConsumer.camelDefaultKafkaReceive();
//    }


}