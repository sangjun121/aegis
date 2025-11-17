package me.sangjun.aegis.core.alert;

import me.sangjun.aegis.core.exception.AegisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AegisErrorLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(AegisErrorLogger.class);

    private static final String SEPARATOR = "=================================================================";
    private static final String ERROR_HEADER = "             Aegis Bootstrap Failed            ";

    public void logError(AegisException exception) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(SEPARATOR).append("\n");
        sb.append(ERROR_HEADER).append("\n");
        sb.append(SEPARATOR).append("\n\n");

        sb.append("[Error Information(예외 정보)]\n");
        sb.append("  Exception: ").append(exception.getClass().getSimpleName()).append("\n");
        sb.append("  Error Code: ").append(exception.getErrorCode().name()).append("\n");
        sb.append("  Message: ").append(exception.getMessage()).append("\n");
        sb.append("\n[Stack Trace]\n");
        LOGGER.error(sb.toString(), exception);
    }

    public void terminatedLog(){
        LOGGER.error("Aegis Bootstrap이 실패하였습니다. 다음과 같은 예외와 함께 어플리케이션이 종료됩니다.");
    }
}
