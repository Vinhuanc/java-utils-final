package kafka.producer;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CamelProducer {
    private static final Logger LOG = LoggerFactory.getLogger(CamelProducer.class);
    public static final String DIRECT_KAFKA_START = "direct:kafkaStart";
    public static final String DIRECT_KAFKA_START_WITH_PARTITIONER = "direct:kafkaStartWithPartitioner";
    public static final String HEADERS = "${headers}";

    private CamelProducer() { }

    public static void main(String[] args) throws Exception {

        LOG.info("About to run Kafka-camel integration...");

        String testKafkaMessage = "Test Message from  MessagePublisherClient " + Calendar.getInstance().getTime();

        try (CamelContext camelContext = new DefaultCamelContext()) {
            // Set the location of the configuration
            camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
            // Set up the Kafka component
            setUpKafkaComponent(camelContext);
            // Add route to send messages to Kafka
      //      camelContext.addRoutes(createRouteBuilder());
            camelContext.start();
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            producerTemplate.sendBody("Hello, today is 9/20, its 1:20pm");


//            try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
//                camelContext.start();
//
//                Map<String, Object> headers = new HashMap<>();
//
//                headers.put(KafkaConstants.PARTITION_KEY, 0);
//                headers.put(KafkaConstants.KEY, "1");
//                producerTemplate.sendBodyAndHeaders(DIRECT_KAFKA_START, testKafkaMessage, headers);
//
//                // Send with topicName in header
//
//                testKafkaMessage = "TOPIC " + testKafkaMessage;
//                headers.put(KafkaConstants.KEY, "2");
//                headers.put(KafkaConstants.TOPIC, "TestLog");
//
//                producerTemplate.sendBodyAndHeaders("direct:kafkaStartNoTopic", testKafkaMessage, headers);
//
//                testKafkaMessage = "PART 0 :  " + testKafkaMessage;
//                Map<String, Object> newHeader = new HashMap<>();
//                newHeader.put(KafkaConstants.KEY, "AB"); // This should go to partition 0
//
//                producerTemplate.sendBodyAndHeaders(DIRECT_KAFKA_START_WITH_PARTITIONER, testKafkaMessage, newHeader);
//
//                testKafkaMessage = "PART 1 :  " + testKafkaMessage;
//                newHeader.put(KafkaConstants.KEY, "ABC"); // This should go to partition 1
//
//                producerTemplate.sendBodyAndHeaders(DIRECT_KAFKA_START_WITH_PARTITIONER, testKafkaMessage, newHeader);
//            }

//            LOG.info("Successfully published event to Kafka.");
//            System.out.println("Enter text on the line below : [Press Ctrl-C to exit.] ");

            Thread.sleep(5L * 60 * 1000);
        }


    static RouteBuilder createRouteBuilder() {
        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {
                from("test-jms:queue:test.queue").to("file://test");
            }
        });
//        return new RouteBuilder() {
//            public void configure() {
//                from(DIRECT_KAFKA_START).routeId("DirectToKafka")
//                        .to("kafka:{{producer.topic}}").log(HEADERS);
//                from("direct:start")
//                  .to("kafka:test?brokers=localhost:9092");
                // Topic can be set in header as well.

//                from("direct:kafkaStartNoTopic").routeId("kafkaStartNoTopic")
//                        .to("kafka:dummy")
//                        .log(HEADERS);

                // Use custom partitioner based on the key.

//                from(DIRECT_KAFKA_START_WITH_PARTITIONER).routeId("kafkaStartWithPartitioner")
//                        .to("kafka:{{producer.topic}}?partitioner={{producer.partitioner}}")
//                        .log(HEADERS);


                // Takes input from the command line.

//                from("stream:in").id("input").setHeader(KafkaConstants.PARTITION_KEY, simple("0"))
//                        .setHeader(KafkaConstants.KEY, simple("1")).to(DIRECT_KAFKA_START);

            }
        };
    }

   public static void setUpKafkaComponent(CamelContext camelContext) {
        // setup kafka component with the brokers
        ComponentsBuilderFactory.kafka()
                .brokers("{{kafka.brokers}}")
                .register(camelContext, "kafka");
    }
}
}