package me.sangjun.aegis.core.bootstrap;

import java.util.Map;
import java.util.Set;
import me.sangjun.aegis.core.api.AegisConfig;
import me.sangjun.aegis.core.api.DomainValidator;
import me.sangjun.aegis.core.binder.DomainValidatorBinder;
import me.sangjun.aegis.core.exception.AegisException;
import me.sangjun.aegis.core.exception.AegisExceptionEntryPoint;
import me.sangjun.aegis.core.scanner.DomainScanner;
import me.sangjun.aegis.core.scanner.ValidatorScanner;

public class AegisBootStrap {

    public static void run(Class<?> primarySource) {
        new AegisBootStrap().start(primarySource);
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
    private void start(Class<?> primarySource) {
        /**
         * TODO:
         * 1. 관리 대상 도메인 등록
         * 2. 관리 대상에 대응하는 Validator 구현 여부 검사
         * 3. 관리 대상 도메인의 필드에 대응하는 validate 메소드 구현 여부 검사
         */

        try {
            Set<Class<?>> domains = domainScanner.scanDomain(primarySource);
            Set<Class<? extends DomainValidator>> validators = validatorScanner.scanValidator(primarySource);
            Map<Class<?>, Class<? extends DomainValidator>> domainValidatorMapping = domainValidatorBinder.bind(domains,
                    validators);
        } catch (AegisException e){
            new AegisExceptionEntryPoint().handle(e);
        }


    }

    /**
     * 도메인 탐색을 Config 파일을 기반으로 관리 대상 도메인을 등록하는 경우 사용한다. Config 파일 외에도, primarySource를 기반으로 하위 @AegisDomain을 탐지하고 관리 대상
     * 도메인을 등록한다.
     *
     * @param primarySource : 사용자 어플리케이션의 main클래스를 의미한다.
     * @param config        : AegisConfig의 구현체로 사용자가 선언 방식 혹은 path 탐색 방식으로 도메인을 등록하는 경우, 이에 대한 정보가 작성된 파일이다.
     */
    public static void run(Class<?> primarySource, AegisConfig config) {
        /**
         * TODO:
         * 1. 관리 대상 도메인 등록
         * 2. 관리 대상에 대응하는 Validator 구현 여부 검사
         * 3. 관리 대상 도메인의 필드에 대응하는 validate 메소드 구현 여부 검사
         */
    }
}
