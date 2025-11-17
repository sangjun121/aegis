package me.sangjun.aegis.core.registry;

import java.util.List;
import java.util.Map;
import java.util.Set;
import me.sangjun.aegis.core.alert.AegisInfoLogger;

public class AegisRegistry {
    private final AegisInfoLogger infoLogger;
    private final RegistryInfo registryInfo;

    public AegisRegistry(Set<Class<?>> domains, Map<Class<?>, Class<?>> domainValidatorMapping,
                         Map<Class<?>, List<Class<?>>> dependencyDomainsMapping) {
        this.registryInfo = new RegistryInfo(domains, domainValidatorMapping, dependencyDomainsMapping);
        this.infoLogger = new AegisInfoLogger();
        toLogger();
    }

    private void toLogger() {
        infoLogger.log(registryInfo);
    }

    public RegistryInfo getRegistryInfo() {
        return registryInfo;
    }
}
