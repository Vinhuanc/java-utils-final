package kafka.consumer;
import beans.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.dataformat.avro.AvroDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.camel.spi.*;
import org.apache.camel.util.concurrent.NamedThreadLocal;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import processor.GenericRecordToMap;
public final class CamelConsumer {
    public static final Logger LOG = LoggerFactory.getLogger(CamelConsumer.class);

    public CamelConsumer() {
    }

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


//    public static void camelDefaultKafkaReceive() {
//        LOG.info("About to run Kafka-camel integration...");
//        CamelContext camelContext = new DefaultCamelContext();
//        LOG.info("starting route:");
//        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
//        LOG.info("About to start route: Kafka Server -> Log ");
//     try {
//         camelContext.addRoutes(new RouteBuilder() {
//             public void configure() throws Exception {
//
//                 from("kafka:{{consumer.topic}}?brokers={{kafka.brokers}}&groupId={{consumer.group}}&valueDeserializer={{value.deserializer}}&schemaRegistryURL={{schema.registry.url}}&specificAvroReader=true&maxPollRecords={{consumer.maxPollRecords}}")
//             //    from("kafka:{{consumer.topic}}?brokers={{kafka.brokers}}&groupId={{consumer.group}}&valueSerializer={{value.serializer}}&schemaRegistryURL={{schema.registry.url}}&specificAvroReader=true&maxPollRecords={{consumer.maxPollRecords}}");
//
//.log("${body}")
//
//                         .routeId("KafkaConsumer");
//             }
//         });
//     } catch (Exception e) {
//         e.printStackTrace();
//     }
//     camelContext.start();
//        ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
//        Endpoint endpoint = camelContext.getEndpoint("KafkaConsumer");
//        Object message = consumerTemplate.receiveBody(endpoint);
//     try {
//         Thread.sleep(60 * 60 * 1000);
//     } catch (InterruptedException e) {
//         e.printStackTrace();
//     }
//     camelContext.stop();
//    }


    public static void main(String[] args) throws InterruptedException, IllegalAccessException {
        CamelContext camelContext = new DefaultCamelContext();
        JaxbDataFormat jaxb = new JaxbDataFormat();
        LOG.info("starting route:");
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
        try {
            camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("kafka:{{consumer.topic}}?brokers={{kafka.brokers}}&groupId={{consumer.group}}&valueSerializer={{value.serializer}}&schemaRegistryURL={{schema.registry.url}}&specificAvroReader=true&maxPollRecords={{consumer.maxPollRecords}}")
                             //    .process(new GenericRecordToMap())
                            //          .unmarshal().jaxb()
                            .routeId("FromKafka")
                            .log("${headers}")
                            .log("${body}");
                   //     .process(new GenericRecordToMap());
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        camelContext.start();
        ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();

        Endpoint endpoint = camelContext.getEndpoint("kafka:{{consumer.topic}}?brokers={{kafka.brokers}}");
        consumerTemplate.receive(endpoint);


//for(String message : Student.class.)
//        Student student = new Student();
//        for (Field field : student.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            String name = field.getName();
//            Object value = field.get(student);
//            String name2 = student.getMajor();
//            System.out.printf("Field name: %s, Field value: %s%n", name, value);
//        Object someObject = student;
//        for (Field field : someObject.getClass().getDeclaredFields()) {
//            field.setAccessible(true); // You might want to set modifier to public first.
//            Object value = field.get(someObject);
//            if (value != null) {
//                System.out.println(field.getName() + "=" + value);
//            }
//        }
//            Consumer<String, GenericRecord> consumer = new KafkaConsumer<>();
//            ConsumerRecords<String, GenericRecord> records = consumer.poll(100);
//            for (ConsumerRecord<String, GenericRecord> record : records) {
//                System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
//            }
//            Map<String, Object> map = new HashMap<>();
//            genericRecord.getSchema().getFields().forEach(field ->
//                    map.put(field.name(), genericRecord.get(field.name())));
            //     System.out.printf("Field name: %s, Field value: %s%n", name, name2);
            // }
//        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
//        System.out.print(gson.fromJson(testStudent.class));
            Thread.sleep(10L * 60 * 1000);
        }

}