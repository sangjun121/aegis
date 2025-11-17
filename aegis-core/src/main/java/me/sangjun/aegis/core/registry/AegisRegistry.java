package me.sangjun.aegis.core.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AegisRegistry {
    private final AegisInfoLogger infoLogger;

    private final Set<Class<?>> domains;
    private final Map<Class<?>, Class<?>> domainValidatorMapping;

    public AegisRegistry(Set<Class<?>> domains, Map<Class<?>, Class<?>> domainValidatorMapping) {
        this.infoLogger = new AegisInfoLogger();
        this.domains = domains;
        this.domainValidatorMapping = domainValidatorMapping;
        toLogger();
    }

    private void toLogger(){
        Map<String, String> mapLogFormat = new HashMap<>();

        for(Map.Entry<Class<?>, Class<?>> entry : domainValidatorMapping.entrySet()){
            mapLogFormat.put(entry.getKey().toString(),entry.getValue().toString());
        }

        infoLogger.log(mapLogFormat);
    }
}
