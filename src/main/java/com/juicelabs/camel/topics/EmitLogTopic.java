package com.juicelabs.camel.topics;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.impl.DefaultCamelContext;

public class EmitLogTopic {
    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {

                from("jetty:http://localhost:10223/topicservice")
                    .setHeader(RabbitMQConstants.ROUTING_KEY, simple("${in.header.level}"))
                    .setBody().simple("${in.header.body}")
                    .to("rabbitmq://localhost?exchange=topic_logs&exchangeType=topic");
            }
        });
        context.start();
    }
}
