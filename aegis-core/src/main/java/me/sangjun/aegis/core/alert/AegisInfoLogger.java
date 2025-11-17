package me.sangjun.aegis.core.registry;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AegisInfoLogger {
    private static final String INFO_LOG_GUIDE_MESSAGE = "[Aegis] Domain-Validator mapping initialized\n";
    private static final String INIT_JSON_FORMAT = "{\n";
    private static final String END_JSON_FORMAT = "}";
    private static final String ENTRY_FORMAT = "  \"%s\": \"%s\",\n";

    private final Logger logger = LoggerFactory.getLogger(AegisInfoLogger.class);

    public void log(Map<String, String> domainValidatorMapping) {
        StringBuilder sb = new StringBuilder();

        sb.append(INFO_LOG_GUIDE_MESSAGE);
        sb.append(INIT_JSON_FORMAT);

        domainValidatorMapping.forEach((domain, validator) ->
                sb.append(String.format(ENTRY_FORMAT, domain, validator))
        );

        if (!domainValidatorMapping.isEmpty()) {
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }
        sb.append(END_JSON_FORMAT);

        logger.info(sb.toString());
    }
}
