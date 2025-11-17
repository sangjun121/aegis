package me.sangjun.aegis.core.registry;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Aegis Bootstrap이 끝난 이후, Aegis의 관리대상으로 등록된 모든 메타데이터를 저장하는 dto
 */
public class RegistryInfo {
    private final Set<Class<?>> domains;
    private final Map<Class<?>, Class<?>> domainValidatorMapping;
    private final Map<Class<?>, List<Class<?>>> dependencyDomainsMapping;

    public RegistryInfo(Set<Class<?>> domains,
                        Map<Class<?>, Class<?>> domainValidatorMapping,
                        Map<Class<?>, List<Class<?>>> dependencyDomainsMapping) {
        this.domains = domains;
        this.domainValidatorMapping = domainValidatorMapping;
        this.dependencyDomainsMapping = dependencyDomainsMapping;
    }

    public Map<Class<?>, Class<?>> getDomainValidatorMapping() {
        return domainValidatorMapping;
    }

    public Map<Class<?>, List<Class<?>>> getDependencyDomainsMapping() {
        return dependencyDomainsMapping;
    }

    public int getTotalDomainCount() {
        return domains.size();
    }

    public int getTotalValidatorCount() {
        return domainValidatorMapping.size();
    }

    public int getTotalDependencyCount() {
        return dependencyDomainsMapping.values().stream()
                .mapToInt(List::size)
                .sum();
    }
}
