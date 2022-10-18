package processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class KafkaAvroProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaAvroProcessor.class);

    @Override
    public void process(Exchange exc) throws Exception {
        List<RecordMetadata> recordMetaData1 = (List<RecordMetadata>) exc.getIn().getHeader(KafkaConstants.KAFKA_RECORDMETA);
        for (RecordMetadata rd: recordMetaData1) {
            LOG.info("producer partition is:"  + rd.partition());
            LOG.info("producer partition message is:"  + rd.toString());
        }
    }
}
