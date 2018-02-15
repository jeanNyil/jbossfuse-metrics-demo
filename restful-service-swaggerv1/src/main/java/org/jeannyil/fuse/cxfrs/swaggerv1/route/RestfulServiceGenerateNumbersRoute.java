package org.jeannyil.fuse.cxfrs.swaggerv1.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.jeannyil.fuse.cxfrs.swaggerv1.constants.ErrorTypesEnum;
import org.jeannyil.fuse.cxfrs.swaggerv1.exceptions.InputParameterValidationException;

public class RestfulServiceGenerateNumbersRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Error handling
        errorHandler(defaultErrorHandler().maximumRedeliveries(0));
        onException(InputParameterValidationException.class)
                .handled(true) // Flag exception as handled
                .logStackTrace(true)
                .logExhausted(true)
                .logHandled(true)
                .doTry()
                .setProperty("errorType", constant(ErrorTypesEnum.VALIDATION_ERROR.toString()))
                // Set the exception message and build the ErrorBean
                .transform().simple("${exception.message}")
                .process("buildErrorBeanProcessor")
                // Transform the ErrorBean message to JSON format
                .marshal().json(JsonLibrary.Jackson, true);
        onException(Exception.class)
                .handled(true) // Flag exception as handled
                .logStackTrace(true)
                .logExhausted(true)
                .logHandled(true)
                .setProperty("errorType", constant(ErrorTypesEnum.ALLOTHER_ERROR.toString()))
                // Set the exception message and build the ErrorBean
                .transform().simple("${exception.message}")
                .process("buildErrorBeanProcessor")
                // Transform the ErrorBean message to JSON format
                .marshal().json(JsonLibrary.Jackson, true);

        /**
         *  Route that implements the GenerateNumbers REST service operation.
         */
        from("direct:getRandomlyGeneratedNumbers")
                .routeId("{{camel.name.route}}-generatenumbers")
                .routePolicyRef("metricsPolicy")
                .log(LoggingLevel.INFO, "Starting the 'Generate Numbers' RESTful service operation...")
                // Generate numbers according to input parameters (count and range)
                .process("generateRandomNumbersProcessor")
                // Transform the Response POJO message to JSON format
                .marshal().json(JsonLibrary.Jackson, true)
                .log(LoggingLevel.INFO, "Generate response:\n ${body}")
                // Collect counter metrics on successfully processed exchange
                .toD("metrics:counter:count.ok.${exchangeProperty.count}")
                .log(LoggingLevel.INFO, "The 'Generate Numbers' RESTful service operation is DONE");
    }
}
