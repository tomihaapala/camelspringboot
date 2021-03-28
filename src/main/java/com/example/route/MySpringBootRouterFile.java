package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import connection.TrainDataConnection;

import java.io.IOException;
import java.util.Arrays;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 */
@Controller
@Component
public class MySpringBootRouterFile extends RouteBuilder {

    public Train[] trains = new Train[500];
    public TrainDataConnection tdc = new TrainDataConnection();

    @Override
    public void configure() throws Exception {
        
        tdc.create();
        from("file:src/data/?noop=true")
                .bean(new Bean());
    }

    @GetMapping("/")
    public String home(Model model) throws Exception {
       
        model.addAttribute("trains", Arrays.asList(trains));
        return "index";
        
    }

    public class Bean {

        public void mapTrains(String body) throws IOException {
            trains = new ObjectMapper().readValue(body, Train[].class);

        }
    }
}

