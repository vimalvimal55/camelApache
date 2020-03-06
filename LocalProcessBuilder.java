package com.learncamel.route;

import com.learncamel.serivce.Item;
import org.apache.camel.Exchange;

public class LocalProcessBuilder implements org.apache.camel.Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        Item item = (Item) exchange.getIn().getBody();

        StringBuilder query = new StringBuilder();
        if (item.getType().equalsIgnoreCase("ADD")) {
            query.append(String.format("INSERT INTO public.item( sku, item_description, price) VALUES ( '%s', '%s', %2.0f)", item.getSku(), item.getItemdescription(), item.getPrice()));


        }
        exchange.getIn().setBody(query.toString());
        System.out.println(query);
    }
}
