package processor;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.reflect.ReflectData;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.PropertyAccessorFactory;

public class MapToGenericRecord implements Processor {
    @Override
    public void process(Exchange exchange) {
        Object object = exchange.getIn().getBody();
        final Schema schema = ReflectData.get().getSchema(object.getClass());
        final GenericData.Record record = new GenericData.Record(schema);
        schema.getFields().forEach(r -> record.put(r.name(), PropertyAccessorFactory.forDirectFieldAccess(object).getPropertyValue(r.name())));
        exchange.getIn().setBody(record);
    }
}
