package kafka.producer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import kafka.consumer.CamelConsumer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.dataformat.avro.AvroDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.metrics.stats.Value;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//SchemaReader()
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
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
public static void main(String[] args) {
  //  public static void camelDefaultProduce() {
        CamelContext camelContext = new DefaultCamelContext();
        LOG.info("starting route:");
    Properties props = new Properties();
  //  producerConfig
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
        setUpKafkaComponent(camelContext);
//        Schema schema = null;
//        try {
//            schema = new Schema.Parser().parse(new File("src/main/resources/schema/student.avsc"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
       //     Schema finalSchema = schema;
            camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {
              //      AvroDataFormat format = new AvroDataFormat(finalSchema);
                    //    JacksonDataFormat format2 = new JacksonDataFormat(CamelProducer.class);
                    from("direct:kafkaStart"
                           + "&keySerializer=" + KafkaAvroSerializer.class.getName()
                           + "&valueSerializer=" + KafkaAvroSerializer.class.getName()
                     //            + "valueSerializer=" + KafkaAvroSerializer.class.getName()
                      //      + "&valueSerializer={{value.serializer}}"
                      //              + "&valueSerializer=" + KafkaAvroSerializer.class.getName()
                                 )
                            .routeId("DirectToKafka")
                            .to("kafka:{{producer.topic}}")
                            .log("${headers}")
                    ;
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            camelContext.start();
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            Map<String, Object> headers = new HashMap<>();
            headers.put("name", "sydney");
            headers.put("major", "BUsiness Adminsitartion");
       //     GenericRecord recordBuilter = new GenericRecord();
//            Schema.Parser parser = new Schema.Parser();
//
//            Schema schema = parser.parse("schema/student.avsc");
//            GenericRecordBuilder recordBuilter = new GenericRecordBuilder(schema);
//            recordBuilter.set("name", "emma");
//            recordBuilter.set("major", "economics admisntiona");


//            headers.put("problem1", "xyz");
//            headers.put("problem2", "");
//            headers.put("problem3", "aaa");
//            headers.values().toString();
         //   byte[] bytes = headers.values().getBytes(StandardCharsets.UTF_8);

       //     String s = new String(headers.values(), StandardCharsets.UTF_8);
          //  headers.put("major", "AerospaceEngineering");
     //       producerTemplate.sendBodyAndHeader("kafka:{{producer.topic}}", "Hello? its 12:26pm", "name", "eli");
//producerTemplate.sendBodyAndHeaders("kafka:{{producer.topic}}", headers, headers);
//producerTemplate.sendBody("kafka:{{producer.topic}}", "{\"hello\":\"word\"}");
  producerTemplate.sendBodyAndHeaders("kafka:{{producer.topic}}", headers,headers);
 // producerTemplate.sendBodyAndHeaders("kafka:{{producer.topic}}", "headers", headers);

          //  LOG.info("Successfully published event to Kafka.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(5L * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// }

//    void sendBody(Endpoint endpoint, Object body) throws CamelExecutionException;
//
//    void sendBody(String endpointUri, Object body) throws CamelExecutionException;
//void sendBodyAndHeaders(String endpointUri, Object body, Map<String, Object> headers) throws CamelExecutionException;
//
//    void sendBodyAndHeaders(Endpoint endpoint, Object body, Map<String, Object> headers) throws CamelExecutionException;