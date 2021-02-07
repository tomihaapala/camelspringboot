package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints
 * to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    private Train[] trains = new Train[500];
    //private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() {

        restConfiguration()
                // .component("servlet")

                .bindingMode(RestBindingMode.auto);

        rest("/harkka/")
                .produces("text/plain")
                .get("hello")
                .route()
                .transform().constant("Hello World!")
                .endRest()
                
                
                .post("hello").type(Train[].class)
                .route()
                .to("log:mylogger?showAll=true")
                .process(new Processor() {

                    @Override
                    public void process(Exchange exchange) throws Exception {

                        trains = exchange.getMessage().getBody(Train[].class);
                        System.out.println("Merkintöjä: " + trains.length);
                        listaa();
                    }

                })
                .transform(simple("POST onnistui: "))
                // .bean("hello")
                .log("LOGIIN")
                .endRest();

    }

    public void listaa() {
        for (int i = 0; i < this.trains.length; i++) {
            if (!this.trains.equals(null)) {

                System.out.println(trains[i]);

            }
        }
    }

    public Train[] returnTrains() {
        return this.trains;
    }

}
