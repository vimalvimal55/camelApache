package com.learncamel.route;

import org.apache.camel.Exchange;

public class SuccessProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("added successfully");
    }
}
