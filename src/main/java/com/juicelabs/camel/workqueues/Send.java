package com.juicelabs.camel.workqueues;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class Send {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("timer://simple?period=1").setBody().simple("Test message: ${in.headers.CamelTimerCounter}")
                    .to("rabbitmq://localhost?queue=task_queue&durable=true&messageProperties=PERSISTENT_TEXT_PLAIN");
            }
        });
        context.start();
    }

}
