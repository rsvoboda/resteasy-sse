# resteasy-sse
JAX-RS 2.1 Server Side Eventing Example (SSE)

## Building JAX-RS 2.1 API, implementation and WildFly server
You need to install locally JAX-RS 2.1 API resteasy branch and RESTEasy jsr370 branch
 * https://github.com/asoldano/api/tree/resteasy
 * https://github.com/asoldano/Resteasy/tree/jsr370
 
You also need WildFly instance with installed JAX-RS 2.1 API and RESTEasy JAX-RS 2.1 implementation.
To install RESTEasy jsr370 branch bits you will run `mvn install`, so the easiest option is to use already patched WildFly instance from the testsuite - for example
 `testsuite/integration-tests/target/test-server/wildfly-10.1.0.Final`.
 
## Building and deploying the application
All you need is to invoke Maven and copy bits to `deployments` directory of running WildFly server.
```
mvn package && cp target/resteasy-sse.war /path/to/wildfly-10.1.0.Final-SSE/standalone/deployments/
```

## Client application
JS based client logic is included in index.html of the deployed web application.

Open http://127.0.0.1:8080/resteasy-sse/index.html or http://127.0.0.1:8080/resteasy-sse/multi.html in your web browser
