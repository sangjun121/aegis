package me.sangjun.aegis.core.exception;

import me.sangjun.aegis.core.alert.AegisErrorLogger;

public class AegisExceptionEntryPoint {
    private final AegisErrorLogger logger;

    public AegisExceptionEntryPoint() {
        this.logger = new AegisErrorLogger();
    }

    public void handle(AegisException exception) {
        logger.logError(exception);
        logger.terminatedLog();
        throw exception;
    }
}
