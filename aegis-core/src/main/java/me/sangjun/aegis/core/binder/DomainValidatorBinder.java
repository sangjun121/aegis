package me.sangjun.aegis.core.binder;

import static me.sangjun.aegis.core.exception.AegisErrorMessage.INVALID_VALIDATOR_DOMAIN;
import static me.sangjun.aegis.core.exception.AegisErrorMessage.VALIDATOR_DUPLICATED;
import static me.sangjun.aegis.core.exception.AegisErrorMessage.VALIDATOR_TYPE_NULL;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import me.sangjun.aegis.core.api.DomainValidator;
import me.sangjun.aegis.core.exception.AegisException;

public class DomainValidatorBinder {

    /**
     * DomainValidator<T>의 구현체인 validators를 대상으로 T 타입(Class)을 리플렉션으로 추출한다. 그 이후, 도메인과 validator가 1대1로 정확히 대응하는지 검사한다.
     */
    public Map<Class<?>, Class<? extends DomainValidator>> bind(Set<Class<?>> domains,
                                                                Set<Class<? extends DomainValidator>> validators) {

        Map<Class<?>, Class<? extends DomainValidator>> domainValidatorMap = new HashMap<>();

        for (Class<? extends DomainValidator> validator : validators) {
            Class<?> domain = extractTypeArgumentFrom(validator);

            if (domain == null) {
                throw new AegisException(VALIDATOR_TYPE_NULL.getMessage());
            }

            if (domainValidatorMap.containsKey(domain)) {
                throw new AegisException(VALIDATOR_DUPLICATED.getMessage());
            }

            if (!domains.contains(domain)) {
                throw new AegisException(INVALID_VALIDATOR_DOMAIN.getMessage());
            }

            domainValidatorMap.put(domain, validator);
        }

        return domainValidatorMap;
    }

    /**
     * DomainValidator 구현체의 타입 변수를 추출하는 메소드. 즉, DomainValidator이 어느 도메인의 Validator인지 추출하는 메소드
     *
     * @param validator
     * @return
     */
    private Class<?> extractTypeArgumentFrom(Class<? extends DomainValidator> validator) {
        for (Type type : validator.getGenericInterfaces()) {
            ParameterizedType pt = (ParameterizedType) type;
            if (pt.getRawType().equals(DomainValidator.class)) {
                Type argument = pt.getActualTypeArguments()[0];
                if (argument instanceof Class<?> domainType) {
                    return domainType;
                }
            }
        }

        throw new AegisException(INVALID_VALIDATOR_DOMAIN.getMessage());
    }
}
