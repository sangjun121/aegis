package me.sangjun.aegis.core.bootstrap;

import java.util.Map;
import java.util.Set;
import me.sangjun.aegis.core.registry.AegisRegistry;
import me.sangjun.aegis.core.api.AegisConfig;
import me.sangjun.aegis.core.binder.DomainValidatorBinder;
import me.sangjun.aegis.core.exception.AegisException;
import me.sangjun.aegis.core.exception.AegisExceptionEntryPoint;
import me.sangjun.aegis.core.scanner.DomainScanner;
import me.sangjun.aegis.core.scanner.ValidatorScanner;

public class AegisBootStrap {

    public static void run(Class<?> primarySource, AegisConfig config) {
        new AegisBootStrap().start(primarySource, config);
    }

    private final DomainScanner domainScanner;
    private final ValidatorScanner validatorScanner;
    private final DomainValidatorBinder domainValidatorBinder;

    private AegisBootStrap() {
        this.domainScanner = new DomainScanner();
        this.validatorScanner = new ValidatorScanner();
        this.domainValidatorBinder = new DomainValidatorBinder();
    }

    /**
     * Config 파일 없이 관리 대상 도메인을 등록하는 경우 사용 가능하다. 따라서, @AegisDomain을 기반으로만 관리 대상 도메인을 등록한다.
     *
     * @param primarySource : 사용자 어플리케이션의 main클래스를 의미한다.
     */
    private void start(Class<?> primarySource, AegisConfig config) {
        try {
            Set<Class<?>> domains = domainScanner.scan(primarySource, config.basePackages());
            Set<Class<?>> validators = validatorScanner.scan(primarySource, config.basePackages());
            Map<Class<?>, Class<?>> domainValidatorMapping = domainValidatorBinder.bind(domains,
                    validators);

            new AegisRegistry(domains, domainValidatorMapping);
        } catch (AegisException e) {
            new AegisExceptionEntryPoint().handle(e);
        }
    }
}
