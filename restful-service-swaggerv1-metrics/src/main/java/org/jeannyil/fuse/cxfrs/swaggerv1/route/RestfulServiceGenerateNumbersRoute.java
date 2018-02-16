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
                .marshal().json(JsonLibrary.Jackson, true)
                // Collect counter metrics on requests that failed validation
                .toD("metrics:counter:${routeId}.validation-ko");
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
                .marshal().json(JsonLibrary.Jackson, true)
                // Collect counter metrics on requests that failed for all other reasons
                .toD("metrics:counter:${routeId}.other-ko");

        /**
         *  Route that implements the GenerateNumbers REST service operation.
         */
        from("direct:getRandomlyGeneratedNumbers")
                .routeId("{{camel.name.route}}-generatenumbers")
                .log(LoggingLevel.INFO, "Starting the 'Generate Numbers' RESTful service operation...")
                // Generate numbers according to input parameters (count and range)
                .process("generateRandomNumbersProcessor")
                // Transform the Response POJO message to JSON format
                .marshal().json(JsonLibrary.Jackson, true)
                .log(LoggingLevel.INFO, "Generate response:\n ${body}")
                // Collect counter metrics on successfully processed exchanges
                .toD("metrics:counter:${routeId}.ok")
                .log(LoggingLevel.INFO, "The 'Generate Numbers' RESTful service operation is DONE");
    }
}
