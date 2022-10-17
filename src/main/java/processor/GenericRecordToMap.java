package processor;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.util.HashMap;
import java.util.Map;
public class GenericRecordToMap implements Processor {
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

    @Override
    public void process(Exchange exchange) throws Exception {
        Object object = exchange.getIn().getBody();
        final Schema schema = ReflectData.get().getSchema(object.getClass());
        GenericData.Record record = new GenericData.Record(schema);

        Map<Object, Object> map = new HashMap<>();
        record.getSchema().getFields().forEach(field ->
                map.put(field.name(), record.get(field.name())));
        exchange.getIn().setBody(record);
        exchange.toString();
    }
}
