package processor;

import beans.Student;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Builder;
import static beans.Student.*;

public class KafkaAvroMessageProcessor implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaAvroMessageProcessor.class);


    @Override
    public void process(Exchange exchange) throws Exception {
        /* Employee emp = Employee.newBuilder()
        .setFirstName("kakarla")
        .setLastName("Ranjith")
        .setBirthDate(new java.util.Date().getTime())
        .build();
        exc.getOut().setBody(emp);*/

//        Widget testWidget = Widget.builder()
//                .name("foo")
//                .id(1)
//                .build();
        
//        Student std = Student.newBuilder()
//                .setName("Emily")
//                .setMajor("Airport Administration")
//                .build();
//        exchange.getOut().setBody(std);

    }
}
