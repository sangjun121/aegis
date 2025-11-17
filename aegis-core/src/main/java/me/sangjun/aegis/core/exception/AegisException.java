package me.sangjun.aegis.core.exception;

public class AegisException extends RuntimeException {
    private final AegisErrorCode errorCode;

    public AegisException(AegisErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AegisErrorCode getErrorCode() {
        return errorCode;
    }
}
