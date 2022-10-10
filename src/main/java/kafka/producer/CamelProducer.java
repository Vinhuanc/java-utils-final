package kafka.producer;

import kafka.consumer.CamelConsumer;
import org.apache.avro.Schema;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.dataformat.avro.AvroDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;
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

    public CamelProducer() throws IOException {
    }
//    Schema schema = new Schema.Parser().parse(new File("student.avsc"));
//    AvroDataFormat format = new AvroDataFormat(schema);

    public static void setUpKafkaComponent(CamelContext camelContext) {
        // setup kafka component with the brokers
        ComponentsBuilderFactory.kafka()
                .brokers("{{kafka.brokers}}")
                .register(camelContext, "kafka");
    }

    public static void camelDefaultProduce() {
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
        //   AvroDataFormat format = new AvroDataFormat(finalSchema);

        try {
            Schema finalSchema = schema;

            camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    AvroDataFormat format = new AvroDataFormat(finalSchema);
                    //    JacksonDataFormat format2 = new JacksonDataFormat(CamelProducer.class);
                    from("direct:kafkaStart")
                            //      .marshal(format)
                            //           .marshal(format)
                            //     .routeId("DirectToKafka")
                            .to("direct:kafkaStart")
                            .log("Today its 10/6/22 its 10:46am");

//                    from("direct:in")
//                            .marshal(format)
//                            .to("direct:marshal");
                    //this oen works with the consumer
//                    from("direct:in")
//                            .marshal(format)
//                            .to("direct:marshal");


                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            camelContext.start();
            System.out.print("Does the code stop here? or even get to his point?");
         //   Map<String, Object> headers = new HashMap<>();

            //  headers.put(KafkaConstants.PARTITION_KEY, 0);
            //   headers.put(KafkaConstants.KEY, "1");
            //    producerTemplate.sendBodyAndHeaders("direct:kafkaStart",""name", "eli"" , headers);
//            headers.put("name", "eli");
//            headers.put("major", "Aerospace Engineering");
//            headers.put("aerospace", "amx");
//            headers.put("name", "eli2");
//            headers.put("major", "Aerospace Engineering2");
//            headers.put("major", "Aerospace Engineering3");
//            headers.put("name", "eli3");
            //          producerTemplate.sendBodyAndHeaders("direct:kafkaStart", headers );
//producerTemplate.sendBody("direct:kafkaStart", headers); //returns INFO FromKafka - {major=Aerospace Engineering, name=eli}
            //   producerTemplate.sendBodyAndHeaders("direct:kafkaStart", headers.get("name") , headers);
            //    producerTemplate.sendBodyAndHeader("direct:kafkaStart", headers.);
            //          producerTemplate.send
                 producerTemplate.sendBody("direct:kafkaStart", "Today is 10/6/22");
            LOG.info("Successfully published event to Kafka.");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(5L * 60 * 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testSendToSpecificUri() {
        CamelContext camelContext = new DefaultCamelContext();
        LOG.info("starting route:");
        //missing application.properties
        //  setUpKafkaComponent(camelContext);
        try {
            camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {

                    from("direct:in")

                            .to("direct:kafkaStart")
                            .log("Today is 10/6/22");
                }
            }));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//    try {
//        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
//        camelContext.start();
//        producerTemplate.sendBody("direct:kafkaStart", "Today is 10/6/22");
//        LOG.info("Successfully published event to Kafka.");
//    }catch (Exception e) {
//        e.printStackTrace();
//    }


//    try {
//        Thread.sleep(5L * 60 * 1000);
//
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//    }
    }
        public static void tryThis(){
        //    Exchange exchange = new Exchange();
            CamelContext camelContext = new DefaultCamelContext();
            ProducerTemplate template = camelContext.createProducerTemplate();

// send to default endpoint
     //       template.sendBody("<hello>world!</hello>");

// send to a specific queue
     //       template.sendBody("test1", "<hello>world!</hello>");
template.sendBody("test1", "Hello please work right now mfffff");
// send with a body and header
//            template.sendBodyAndHeader("activemq:MyQueue",
//                    "<hello>world!</hello>",
//                    "CustomerRating", "Gold");
        }



}

//    void sendBody(Endpoint endpoint, Object body) throws CamelExecutionException;
//
//    void sendBody(String endpointUri, Object body) throws CamelExecutionException;
//void sendBodyAndHeaders(String endpointUri, Object body, Map<String, Object> headers) throws CamelExecutionException;
//
//    void sendBodyAndHeaders(Endpoint endpoint, Object body, Map<String, Object> headers) throws CamelExecutionException;