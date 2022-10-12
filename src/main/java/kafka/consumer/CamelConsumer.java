package kafka.consumer;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.dataformat.avro.AvroDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.camel.spi.*;
import org.apache.camel.util.concurrent.NamedThreadLocal;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class CamelConsumer {
    public static final Logger LOG = LoggerFactory.getLogger(CamelConsumer.class);
    public CamelConsumer(){}

    public static void setUpKafkaComponent(CamelContext camelContext) {
        // setup kafka component with the brokers
        ComponentsBuilderFactory.kafka()
                .brokers("{{kafka.brokers}}")
                .register(camelContext, "kafka");
    }
//THIS ONE WORKS. JUST CONSUMES MESSAGES FROM KAFKA. PROBLEM: DOESN"T SERIALIZE MESSAGES, NEED TO DESERIALIZE
    //default camel consumer from kafka, able to consume messages from the application.properties properties file
//    public static void camelDefaultKafkaReceive() {
//        CamelContext camelContext = new DefaultCamelContext();
//        LOG.info("starting route:");
//        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
//        setUpKafkaComponent(camelContext);
//        try {
//            camelContext.addRoutes(new RouteBuilder() {
//                @Override
//                public void configure() throws Exception {
//                    from("kafka:{{consumer.topic}}"
//                            + "?maxPollRecords={{consumer.maxPollRecords}}"
//                            + "&consumersCount={{consumer.consumersCount}}"
//                            + "&seekTo={{consumer.seekTo}}"
//                            + "&groupId={{consumer.group}}")
//                            .routeId("FromKafka")
//                            .log("${body}");
//                    ;
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        camelContext.start();
//        try {
//            Thread.sleep(90000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }


   //unsure if this is the way to validate the schema???
//    public static void camelSchemaReceive( String fromURI, String routeId) {
//        CamelContext camelContext = new DefaultCamelContext();
//        LOG.info("starting route:");
//        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
//        setUpKafkaComponent(camelContext);
//        Schema schema = null;
//        try {
//            schema = new Schema.Parser().parse(new File("src/main/resources/schema/student.avsc"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        AvroDataFormat format = new AvroDataFormat(schema);
//        try {
//            camelContext.addRoutes(new RouteBuilder() {
//                @Override
//                public void configure() throws Exception {
//                    from("kafka:{{consumer.topic}}"
//                            + "?maxPollRecords={{consumer.maxPollRecords}}"
//                            + "&consumersCount={{consumer.consumersCount}}"
//                            + "&seekTo={{consumer.seekTo}}"
//                            + "&groupId={{consumer.group}}")
//                            .routeId("FromKafka")
//                            .log("${body}");
//                    if(fromURI != null && routeId != null){
//                        from(fromURI)
//                                .routeId(routeId)
//                                .log("${body}");
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        camelContext.start();
//        try {
//            Thread.sleep(90000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }


    public static void camelDefaultKafkaReceive() {
        LOG.info("About to run Kafka-camel integration...");
//        Properties props = new Properties();
//        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
//        props.put("schema.registry.url", "http://localhost:8081");
//        props.put("key.converter.schema.registry.url", "http://localhost:8081");
//        props.put("value.converter.schema.registry.url", "http://localhost:8081");
        CamelContext camelContext = new DefaultCamelContext();
     try {
         camelContext.addRoutes(new RouteBuilder() {
             public void configure() throws Exception {
                 camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
                 log.info("About to start route: Kafka Server -> Log ");
                 from("kafka:{{consumer.topic}}?brokers=localhost:9092"
                         + "&maxPollRecords={{consumer.maxPollRecords}}"
                         + "&consumersCount={{consumer.consumersCount}}"
                         + "&seekTo={{consumer.seekTo}}"
                         + "&groupId={{consumer.group}}"
                         //        +   "&valueDeserializer=org.apache.kafka.common.serialization.ByteArrayDeserializer"
                                 //           + "keyDeserializer=" + StringDeserializer.class.getName()
                       //          + "valueDeserializer=" + StringDeserializer.class.getName()
                  //              + "valueDeserializer=#kafkaHeaderDeserializerCustom"
                 //        + "&schemaRegistryURL={{http://localhost:8081}}"
             //                    + "headerDeserializer=#kafkaHeaderDeserializerCustom"
                         //    + "&kafkaHeaderDeserializer=#kafkaHeaderDeserializerCustom"
                      //   + "&key.converter.schema.registry.url={{"
//                         + "&keyDeserializer=" + StringDeserializer.class.getName()
                   //      + "valueDeserializer=" + StringDeserializer.class
                                 + "valueDeserializer=" + KafkaAvroDeserializer.class
                 )
                         .routeId("DirectToKafka")
                    //good     .routeId("FromKafka")
                      //   .log("${headers}");
                      //   .log("${headers.values()}");
                        // .log("${values}");
                         .log("${headers}");
             }
         });
     } catch (Exception e) {
         e.printStackTrace();
     }
     camelContext.start();
    //    String value = new String(headers.value(), StandardCharsets.UTF_8);
  //   System.out.print("*******" + value "*********");
     try {
         Thread.sleep(30 * 60 * 1000);
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
     camelContext.stop();
    }
}