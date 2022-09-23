package kafka.producer;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//SchemaReader()
import java.util.HashMap;
import java.util.Map;

//public final class CamelProducer {
//    private static final Logger LOG = LoggerFactory.getLogger(CamelProducer.class);
//    public CamelProducer() { }
//
//    public static void setUpKafkaComponent(CamelContext camelContext) {
//        // setup kafka component with the brokers
//        ComponentsBuilderFactory.kafka()
//                .brokers("{{kafka.brokers}}")
//                .register(camelContext, "kafka");
//    }
//
//     public static RouteBuilder createRouteBuilder() {
//        return new RouteBuilder() {
//            public void configure() {
//                from("direct:kafkaStart").routeId("DirectToKafka")
//                        .to("kafka:{{producer.topic}}").log("${headers}");
//            }
//        };
//    }

//    public static void sendMessage(String toURI, String message, Map<String,String> headers){
//        CamelContext camelContext = new DefaultCamelContext();
//        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
//        producerTemplate.sendBodyAndHeader(toURI, message, headers);
//    }
//    public static void createCamelContext() throws Exception {
//        CamelContext camelContext = new DefaultCamelContext();
//        LOG.info("starting route:");
//        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
//        setUpKafkaComponent(camelContext);
//        camelContext.addRoutes(createRouteBuilder());
//        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
//        camelContext.start();
//
//        Map<String, Object> headers = new HashMap<>();
//
//        headers.put(KafkaConstants.PARTITION_KEY, 0);
//        headers.put(KafkaConstants.KEY, "1");
//        producerTemplate.sendBodyAndHeaders("direct:kafkaStart", "insert the avro schema here", headers);
//
//    //    right here sendMessage method
//
//        Thread.sleep(5L * 60 * 1000);
//    }






//}
