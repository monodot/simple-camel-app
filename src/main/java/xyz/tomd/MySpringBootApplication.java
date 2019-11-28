package xyz.tomd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:/META-INF/spring/beans.xml"})
public class MySpringBootApplication {

    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        // See: https://stackoverflow.com/questions/48837173/unable-to-intercept-applicationenvironmentpreparedevent-in-spring-boot
        SpringApplication.run(MySpringBootApplication.class, args);
    }

}
