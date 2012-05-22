package com.juicelabs.camel.topics;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ReceiveLogsTopic {
    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("rabbitmq://localhost?exchange=topic_logs&exchangeType=topic&bindingKey=*.error")
                    .setBody().simple("${in.header.RabbitMQRoutingKey}: ${in.body}")
                    .to("stream:out");
            }
        });

        context.setTracing(true);
        context.start();
    }

}
