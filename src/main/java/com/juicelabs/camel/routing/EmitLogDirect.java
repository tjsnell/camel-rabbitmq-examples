package com.juicelabs.camel.routing;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.impl.DefaultCamelContext;

public class EmitLogDirect {

    public static void main(String args[]) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {

                from("jetty:http://localhost:10222/logservice")
//                    .process(new LogsProcessor())
                    .setHeader(RabbitMQConstants.ROUTING_KEY, simple("${in.header.level}"))
                    .setBody().simple("${in.header.body}")
                    .to("rabbitmq://localhost?exchange=direct_logs&exchangeType=direct");
            }
        });
        context.start();
    }

}

enum LEVEL {
    INFO, WARN, ERROR
}

class LogsProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        String level = exchange.getIn().getHeader("level", String.class);
        LEVEL logLevel;
        if (level == null) {
            throw new Exception("Level not set");
        }

        try {

            // check for valid level
            logLevel = LEVEL.valueOf(level.toUpperCase());
            System.out.println("Lo  : " + logLevel);


            String body = exchange.getIn().getHeader("body", String.class);

            exchange.getIn().setBody(body);
            exchange.getIn().setHeader(RabbitMQConstants.ROUTING_KEY, logLevel.toString());
        } catch (IllegalArgumentException e) {
           exchange.setException(new IllegalArgumentException("Invalid level: " + level));
        }

    }
}
