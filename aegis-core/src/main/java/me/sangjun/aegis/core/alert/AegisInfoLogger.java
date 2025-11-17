package me.sangjun.aegis.core.alert;

import java.util.List;
import java.util.Map;
import me.sangjun.aegis.core.registry.RegistryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AegisInfoLogger {
    private static final String SEPARATOR = "=================================================================";
    private static final String HEADER = "             Aegis Bootstrap Completed Successfully             ";

    private final Logger logger = LoggerFactory.getLogger(AegisInfoLogger.class);

    public void log(RegistryInfo registryInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(SEPARATOR).append("\n");
        sb.append(HEADER).append("\n");
        sb.append(SEPARATOR).append("\n\n");

        appendSummary(sb, registryInfo);
        appendDomainValidatorMappings(sb, registryInfo);
        appendDependencyRelationships(sb, registryInfo);

        logger.info(sb.toString());
    }

    private void appendSummary(StringBuilder sb, RegistryInfo registryInfo) {
        sb.append("[BootStrap Summary]\n");
        sb.append("  - Total Domains(관리 대상 도메인 개수): ").append(registryInfo.getTotalDomainCount()).append("\n");
        sb.append("  - Domain Validators(관리 대상 검증 클래스 개수): ").append(registryInfo.getTotalValidatorCount())
                .append("\n");
        sb.append("  - Total Validation Dependencies(검증 의존 관계 개수): ").append(registryInfo.getTotalDependencyCount())
                .append("\n");
        sb.append("\n");
    }

    private void appendDomainValidatorMappings(StringBuilder sb, RegistryInfo registryInfo) {
        sb.append("[Domain-Validator Mappings]\n");

        Map<Class<?>, Class<?>> mappings = registryInfo.getDomainValidatorMapping();
        if (mappings.isEmpty()) {
            sb.append("  (No mappings): 예상치 못한 예외가 발생하였습니다. 어플리케이션을 재빌드하시기 바랍니다.\n");
        } else {
            mappings.forEach((domain, validator) -> {
                sb.append("  ").append(getSimpleName(domain))
                        .append(" <-> ")
                        .append(getSimpleName(validator))
                        .append("\n");
            });
        }
        sb.append("\n");
    }

    private void appendDependencyRelationships(StringBuilder sb, RegistryInfo registryInfo) {
        sb.append("[Dependency Relationships]\n");

        Map<Class<?>, List<Class<?>>> dependencies = registryInfo.getDependencyDomainsMapping();
        if (dependencies.isEmpty()) {
            sb.append("  (검증 의존 관계가 존재하지 않습니다.)\n");
        } else {
            dependencies.forEach((domain, dependencys) -> {
                if (!dependencys.isEmpty()) {
                    for (int i = 0; i < dependencys.size(); i++) {
                        sb.append("  ")
                                .append(getSimpleName(domain))
                                .append(" -> ")
                                .append(getSimpleName(dependencys.get(i)))
                                .append("\n");
                    }
                }
            });
        }
    }

    private String getSimpleName(Class<?> clazz) {
        return clazz.getSimpleName();
    }
}
