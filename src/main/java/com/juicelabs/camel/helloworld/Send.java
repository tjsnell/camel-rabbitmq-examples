package com.juicelabs.camel.helloworld;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Send {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("timer://simple?period=1000").setBody().simple("Test message: ${id}")
                    .to("rabbitmq://localhost?queue=hello");
            }
        });
        context.start();
    }

}
