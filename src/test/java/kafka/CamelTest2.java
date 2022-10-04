package kafka;

import kafka.consumer.CamelConsumer;

public class CamelTest2 {
    public static void main(String[] args) {
     //   CamelConsumer.camelReceive("direct:in", "FirectToKafka");
        CamelConsumer.camelDefaultReceive();
    }
}
