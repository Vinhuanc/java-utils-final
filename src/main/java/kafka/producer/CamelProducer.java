package kafka.producer;

import kafka.consumer.CamelConsumer;
import org.apache.avro.Schema;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.dataformat.avro.AvroDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.kafka.common.metrics.stats.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//SchemaReader()
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public  class CamelProducer {
    public static final Logger LOG = LoggerFactory.getLogger(CamelProducer.class);
    public CamelProducer() throws IOException {}
//    Schema schema = new Schema.Parser().parse(new File("student.avsc"));
//    AvroDataFormat format = new AvroDataFormat(schema);

    public static void setUpKafkaComponent(CamelContext camelContext) {
        // setup kafka component with the brokers
        ComponentsBuilderFactory.kafka()
                .brokers("{{kafka.brokers}}")
                .register(camelContext, "kafka");
    }

    public static void camelDefaultProduce(){
        CamelContext camelContext = new DefaultCamelContext();
        LOG.info("starting route:");
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
        setUpKafkaComponent(camelContext);
        Schema schema = null;
        try {
            schema = new Schema.Parser().parse(new File("src/main/resources/schema/student.avsc"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AvroDataFormat format = new AvroDataFormat(schema);

        try {
            camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:kafkaStart")
                         //   .marshal(format)

                            .routeId("DirectToKafka")
                            .to("kafka:{{producer.topic}}")
                            .log("${headers}");
                    from("direct:in")
                            .marshal(format)
                            .to("direct:marshal");
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        camelContext.start();
            Map<String, Object> headers = new HashMap<>();

            headers.put(KafkaConstants.PARTITION_KEY, 0);
            headers.put(KafkaConstants.KEY, "1");
            producerTemplate.sendBodyAndHeaders("direct:kafkaStart", "its 2:07pm", headers);



        LOG.info("Successfully published event to Kafka.");
        }catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(5L * 60 * 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
//    void sendBody(Endpoint endpoint, Object body) throws CamelExecutionException;
//
//    void sendBody(String endpointUri, Object body) throws CamelExecutionException;
//void sendBodyAndHeaders(String endpointUri, Object body, Map<String, Object> headers) throws CamelExecutionException;
//
//    void sendBodyAndHeaders(Endpoint endpoint, Object body, Map<String, Object> headers) throws CamelExecutionException;