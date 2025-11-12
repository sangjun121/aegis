package me.sangjun.aegis.core.scanner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.sangjun.aegis.core.api.AegisConfig;

public class DomainScanner {

    public Set<Class<?>> scanDomain(Class<?> primarySource) {
        Set<Class<?>> domains = new HashSet<>();

        domains.addAll(scanByAnnotation(primarySource));
        return domains;
    }

    public Set<Class<?>> scanDomain(Class<?> primarySource, AegisConfig config) {
        Set<Class<?>> domains = new HashSet<>();

        domains.addAll(scanByConfig(config));
        domains.addAll(scanByAnnotation(primarySource));

        return domains;
    }

    private Set<Class<?>> scanByConfig(AegisConfig config) {
        Set<Class<?>> domains = new HashSet<>();

        domains.addAll(scanByPath(config.baseDomainPaths()));
        domains.addAll(scanByExplicitDeclaration(config.domains()));

        return domains;
    }

    private Set<Class<?>> scanByPath(List<String> baseDomainPaths) {

    }

    private Set<Class<?>> scanByExplicitDeclaration(List<Class<?>> domains) {

    }

    private Set<Class<?>> scanByAnnotation(Class<?> primarySource) {
        Set<Class<?>> domains = new HashSet<>();

    }
}
