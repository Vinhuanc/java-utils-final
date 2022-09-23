package kafka;

import kafka.consumer.CamelConsumer;
import org.apache.camel.CamelContext;
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

    @Test
            public void testCamelConsumer() {
        //failed. duplicate routeId
//        CamelConsumer.startCamel("kafka:{{consumer.topic}}\"\n" +
//                "                            + \"?maxPollRecords={{consumer.maxPollRecords}}\"\n" +
//                "                            + \"&consumersCount={{consumer.consumersCount}}\"\n" +
//                "                            + \"&seekTo={{consumer.seekTo}}\"\n" +
//                "                            + \"&groupId={{consumer.group}}", "FromKafka");

        //If the programmer wants to use the default fromURI and routeId, do this.
        CamelConsumer.startCamel(null, null);

        //If the programmer has different fromURI and routId, do this.
      //  CamelConsumer.startCamel("insertYourFromURIhere", "insertYourRouteIdhere");

    }

}