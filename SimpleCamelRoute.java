package com.learncamel.route;

import com.learncamel.serivce.Item;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by z001qgd on 1/24/18.
 */
@Component
public class SimpleCamelRoute extends RouteBuilder {

    @Autowired
    Environment environment;

    @Autowired
    DataSource datasource;

    @Override
    public void configure() throws Exception {

        DataFormat dataFormat = new BindyCsvDataFormat(Item.class);
//@formatter:off
        from("{{startRoute}}")
                .log("Timer Invoked and the body" + environment.getProperty("message"))
                .choice()
                .when((header("env").isNotEqualTo("mock")))
                    .pollEnrich("{{fromRoute}}")
                    .otherwise()
                    .log("mock env flow and the body is ${body}")
                .end()
                .to("{{toRoute1}}")
                .unmarshal(dataFormat)
                .log("${body}")
                .split(body())
                    .log("${body}")
                    .process(new LocalProcessBuilder())
                    .to("jdbc:dataSource")
                .process(new SuccessProcessor())
                .end();

//@formatter:on

    }
}
