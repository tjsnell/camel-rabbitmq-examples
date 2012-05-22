package com.juicelabs.camel.routing;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ReceiveLogsDirect {
    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("rabbitmq://localhost?exchange=direct_logs&exchangeType=direct&bindingKey=ERROR")
                    .setBody().simple("ERROR: ${in.body}")
                    .to("stream:out");

                from("rabbitmq://localhost?exchange=direct_logs&exchangeType=direct&bindingKey=WARN")
                    .setBody().simple("WARN: ${in.body}")
                    .to("stream:out");

                from("rabbitmq://localhost?exchange=direct_logs&exchangeType=direct&bindingKey=WARN,INFO")
                    .setBody().simple("WARN, INFO: ${in.body}")
                    .to("stream:out");

            }
        });


        context.start();
    }

}
