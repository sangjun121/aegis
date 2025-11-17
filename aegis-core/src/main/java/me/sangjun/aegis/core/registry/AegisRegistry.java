package me.sangjun.aegis.core.registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AegisRegistry {
    private final AegisInfoLogger infoLogger;

    private final Set<Class<?>> domains;
    private final Map<Class<?>, Class<?>> domainValidatorMapping;
    private final Map<Class<?>, List<Class<?>>> dependencyDomainsMapping;

    public AegisRegistry(Set<Class<?>> domains, Map<Class<?>, Class<?>> domainValidatorMapping,
                         Map<Class<?>, List<Class<?>>> dependencyDomainsMapping) {
        this.domains = domains;
        this.domainValidatorMapping = domainValidatorMapping;
        this.dependencyDomainsMapping = dependencyDomainsMapping;

        this.infoLogger = new AegisInfoLogger();
        toLogger();
    }

    private void toLogger() {
        Map<String, String> mapLogFormat = new HashMap<>();

        for (Map.Entry<Class<?>, Class<?>> entry : domainValidatorMapping.entrySet()) {
            mapLogFormat.put(entry.getKey().toString(), entry.getValue().toString());
        }

        infoLogger.log(mapLogFormat);
    }
}
