package me.sangjun.aegis.core.binder;

import static me.sangjun.aegis.core.exception.AegisErrorCode.INVALID_VALIDATOR_DOMAIN;
import static me.sangjun.aegis.core.exception.AegisErrorCode.NON_IMPLEMENT_VALIDATOR;
import static me.sangjun.aegis.core.exception.AegisErrorCode.VALIDATOR_DUPLICATED;
import static me.sangjun.aegis.core.exception.AegisErrorCode.VALIDATOR_TYPE_NULL;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.sangjun.aegis.core.exception.AegisException;

public class DomainValidatorBinder {

    /**
     * DomainValidator<T>의 구현체인 validators를 대상으로 T 타입(Class)을 리플렉션으로 추출한다. 그 이후, 도메인과 validator가 1대1로 정확히 대응하는지 검사한다.
     */
    public Map<Class<?>, Class<?>> bind(Set<Class<?>> domains,
                                        Set<Class<?>> validators) {

        Map<Class<?>, Class<?>> domainValidatorMap = new HashMap<>();

        for (Class<?> validator : validators) {
            Class<?> domain = extractTypeArgumentFrom(validator);

            if (domain == null) {
                throw new AegisException(VALIDATOR_TYPE_NULL);
            }

            if (domainValidatorMap.containsKey(domain)) {
                throw new AegisException(VALIDATOR_DUPLICATED);
            }

            if (!domains.contains(domain)) {
                throw new AegisException(INVALID_VALIDATOR_DOMAIN);
            }

            domainValidatorMap.put(domain, validator);
        }

        return domainValidatorMap;
    }

    public void bindDependency(Map<Class<?>, List<Class<?>>> dependencyDomains,
                               Set<Class<?>> validators) {
        /**
         * DependencyValidator 내부 타입 인자 추출
         */
        List<DependencyKey> dependencyKeys = new ArrayList<>();
        for (Class<?> validator : validators) {
            DependencyKey dependencyKey = extractDependencyKeyFrom(validator);
            dependencyKeys.add(dependencyKey);
        }

        /**
         * 도메인이 의존하는 모든 도메인 리스트(entryDependencyDomains) 중, DependencyKey와 매칭되는 개수 체크
         */

        long totalDomainCount = 0;
        for (Map.Entry<Class<?>, List<Class<?>>> entry : dependencyDomains.entrySet()) {
            List<Class<?>> entryDependencyDomains = entry.getValue();
            long count = dependencyKeys.stream()
                    .filter(dependencyKey -> dependencyKey.source().equals(entry.getKey()))
                    .filter(dependencyKey -> entryDependencyDomains.contains(dependencyKey.dependency()))
                    .count();

            if (count != entryDependencyDomains.size()) {
                throw new AegisException(NON_IMPLEMENT_VALIDATOR);
            }

            totalDomainCount += count;
        }

        if (totalDomainCount != dependencyKeys.size()) {
            throw new AegisException(NON_IMPLEMENT_VALIDATOR);
        }
    }

    /**
     * DomainValidator 구현체의 타입 변수를 추출하는 메소드. 즉, DomainValidator이 어느 도메인의 Validator인지 추출하는 메소드
     *
     * @param validator
     * @return
     */
    private Class<?> extractTypeArgumentFrom(Class<?> validator) {
        for (Type type : validator.getGenericInterfaces()) {
            if (type instanceof ParameterizedType parameterizedType) {
                Type argument = parameterizedType.getActualTypeArguments()[0];
                if (argument instanceof Class<?> domain) {
                    return domain;
                }
            }
        }

        throw new AegisException(INVALID_VALIDATOR_DOMAIN);
    }

    private DependencyKey extractDependencyKeyFrom(Class<?> validator) {
        for (Type type : validator.getGenericInterfaces()) {
            if (type instanceof ParameterizedType parameterizedType) {
                Type sourceArgument = parameterizedType.getActualTypeArguments()[0];
                Type dependencyArgument = parameterizedType.getActualTypeArguments()[1];

                if (sourceArgument instanceof Class<?> source &&
                        dependencyArgument instanceof Class<?> dependency) {
                    return new DependencyKey(source, dependency);
                }
            }
        }

        throw new AegisException(INVALID_VALIDATOR_DOMAIN);
    }
}
