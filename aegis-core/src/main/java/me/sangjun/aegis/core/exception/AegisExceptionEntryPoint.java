package me.sangjun.aegis.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AegisExceptionEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(AegisExceptionEntryPoint.class);

    private static final String ERROR_MESSAGE = "[AegisBootstrapError] {} {}";


    public void handle(AegisException exception) {
        LOGGER.error(ERROR_MESSAGE, exception.getClass().getSimpleName(), exception.getMessage());
        throw exception;
    }
}
