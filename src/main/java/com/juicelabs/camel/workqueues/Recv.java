package com.juicelabs.camel.workqueues;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Recv {

    public static void main(String args[]) throws Exception {

        final Recv recv = new Recv();

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("rabbitmq://localhost?queue=task_queue&durable=true&autoAck=false&prefetch=1")
                    .bean(recv, "doWork")
                    .to("stream:out");
            }
        });
//        context.setTracing(true);
        context.start();
    }

    public void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }


}
