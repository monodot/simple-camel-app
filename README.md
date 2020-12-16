# simple-camel-app

To run:

    mvn clean spring-boot:run
    
This will start a REST service on port 8080 using the embedded servlet container, Undertow:

    $ curl http://localhost:8080/camel/hello
    Hello world!

Note that the default servlet path for Camel is `/camel`.
