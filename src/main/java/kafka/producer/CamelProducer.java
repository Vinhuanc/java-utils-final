package kafka.producer;

import beans.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import processor.MapToGenericRecord;

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
public static void main(String[] args) throws Exception {
  //  public static void camelDefaultProduce() {
        CamelContext camelContext = new DefaultCamelContext();
        LOG.info("starting route:");
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");

//        Schema schema = null;
//        try {
//            schema = new Schema.Parser().parse(new File("src/main/resources/schema/student.avsc"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

       //     Schema finalSchema = schema;
            camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {
              //      AvroDataFormat format = new AvroDataFormat(finalSchema);
                    //    JacksonDataFormat format2 = new JacksonDataFormat(CamelProducer.class);
                    from("direct:kafkaStart")
                         //   .process(new MapToGenericRecord())
                            .setBody(constant("Hi This is Avro example"))
                            .process(new KafkaAvroMessageProcessor())
                          //  .log("${body}")
//                            .marshal().jaxb()

                            .to("kafka:{{producer.topic}}?brokers={{kafka.brokers}}&clientId={{producer.clientId}}&valueSerializer={{value.serializer}}&schemaRegistryURL={{schema.registry.url}}&specificAvroReader=true");
                       //     .log("${body}");
                }
            }));

            camelContext.start();
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            Student testStudent= new Student();
            testStudent.setMajor("biology");
            testStudent.setName("Groot56");
    Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    gson.toJson(testStudent);
            Endpoint endpoint = camelContext.getEndpoint("direct:kafkaStart");
            producerTemplate.setDefaultEndpoint(endpoint);
            producerTemplate.sendBody(testStudent);
            LOG.info("Successfully published event to Kafka.");
       //     GenericRecord recordBuilter = new GenericRecord();
//            Schema.Parser parser = new Schema.Parser();
//
//            Schema schema = parser.parse("schema/student.avsc");
//            GenericRecordBuilder recordBuilter = new GenericRecordBuilder(schema);
//            recordBuilter.set("name", "emma");
//            recordBuilter.set("major", "economics admisntiona");

            Thread.sleep(10L * 60 * 1000);

    }}


//
//String schema = "{\n" +
//        "  \"type\": \"record\",\n" +
//        "  \"name\": \"students\",\n" +
//        "  \"fields\": [\n" +
//        "    {\n" +
//        "      \"name\": \"Name\",\n" +
//        "      \"type\": \"string\"\n" +
//        "    },\n" +
//        "    {\n" +
//        "      \"name\": \"major\",\n" +
//        "      \"type\": \"string\"\n" +
//        "    }\n" +
//        "  ]\n" +
//        "} "
//
//        curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
//                --data '{"schema": "{\n" +
//                "  \"type\": \"record\",\n" +
//                "  \"name\": \"students\",\n" +
//                "  \"fields\": [\n" +
//                "    {\n" +
//                "      \"name\": \"Name\",\n" +
//                "      \"type\": \"string\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"name\": \"major\",\n" +
//                "      \"type\": \"string\"\n" +
//                "    }\n" +
//                "  ]\n" +
//                "} "}' \
//                http://localhost:8081/subjects/camelTopicTest-value/versions
//
//                curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" --data '{"schema":"{\n" +
//                "  \"type\": \"record\",\n" +
//                "  \"name\": \"students\",\n" +
//                "  \"fields\": [\n" +
//                "    {\n" +
//                "      \"name\": \"Name\",\n" +
//                "      \"type\": \"string\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"name\": \"major\",\n" +
//                "      \"type\": \"string\"\n" +
//                "    }\n" +
//                "  ]\n" +
//                "} "}' http://localhost:8081/subjects/camelTopicTest-value/versions