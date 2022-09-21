package kafka;
import kafka.consumer.*;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.junit.Test;



public class CamelTest {



        @Test
        public void testCamelConsumer() throws Exception {
            ReusableCamelConsumer consumer = new ReusableCamelConsumer();
         //   consumer.createRouteBuilder();
            consumer.createCamelContext();





        }



}
