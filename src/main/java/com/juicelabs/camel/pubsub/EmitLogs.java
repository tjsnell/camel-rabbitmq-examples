package com.juicelabs.camel.pubsub;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class EmitLogs {
    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("timer://simple?period=500").setBody().simple("Log message: ${in.headers.CamelTimerCounter}")
                    .to("rabbitmq://localhost?exchange=logs&exchangeType=fanout");
            }
        });
        context.start();
    }

}
