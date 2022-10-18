package processor;

//import beans.Student;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import beans.*;
//import static beans.Student.*;

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

        Students student1 = new Students();
      //Students student1 = new Students("emma", "economics");
      student1.setMajor("economics");
      student1.setName("emma");

        exchange.getOut().setBody(student1);


    }
}
