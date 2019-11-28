package xyz.tomd;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    @Override
    public void configure() {
        from("jetty:http://0.0.0.0:8088/hello")
                .setBody(constant("Hello world!"));

        from("timer:mytimer?period=5000")
                .bean("myBean");
    }

}
