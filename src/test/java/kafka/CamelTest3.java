package kafka;

import kafka.producer.CamelProducer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.MapToGenericRecord;

public class CamelTest3 {
    public static final Logger LOG = LoggerFactory.getLogger(CamelProducer.class);
    public static void produce(){
        CamelContext camelContext = new DefaultCamelContext();
        LOG.info("starting route:");
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");
        try {
            camelContext.addRoutes((new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    //      AvroDataFormat format = new AvroDataFormat(finalSchema);
                    //    JacksonDataFormat format2 = new JacksonDataFormat(CamelProducer.class);
                    from("direct:kafkaStart")
                            .process(new MapToGenericRecord())
                            .log("${body}")
                //            .marshal().jaxb()
                            .to("kafka:{{producer.topic}}?brokers={{kafka.brokers}}&clientId={{producer.clientId}}&valueSerializer={{value.serializer}}&schemaRegistryURL={{schema.registry.url}}&specificAvroReader=true")
                            .log("${body}");
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        camelContext.start();

        String userSchema =" {\n" +
                "  \"type\": \"record\",\n" +
                "  \"name\": \"students\",\n" +
                "  \"fields\": [\n" +
                "    {\n" +
                "      \"name\": \"Name\",\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"major\",\n" +
                "      \"type\": \"string\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(userSchema);
        GenericRecord avroRecord = new GenericData.Record(schema);
        avroRecord.put("Name", "Sydney");
        avroRecord.put("major", "business administration");
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        Endpoint endpoint = camelContext.getEndpoint("direct:kafkaStart");
        producerTemplate.setDefaultEndpoint(endpoint);
        Object msg = avroRecord.getClass();
//producerTemplate.sendBody("Hello today is 10/17/22");
//        Message message = exchange.getMessage();
//        Document document = message.getBody(Document.class);
        producerTemplate.sendBody("kafka:{{producer.topic}}?brokers={{kafka.brokers}}", msg);
    }

    public static void main(String [] args){
        produce();
    }
}
