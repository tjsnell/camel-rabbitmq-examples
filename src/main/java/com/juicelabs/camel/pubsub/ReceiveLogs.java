package com.juicelabs.camel.pubsub;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ReceiveLogs {
    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("rabbitmq://localhost?exchange=logs&exchangeType=fanout")
                    .to("stream:out");
            }
        });
        context.start();
    }


}
