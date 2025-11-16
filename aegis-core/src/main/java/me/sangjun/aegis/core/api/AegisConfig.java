package me.sangjun.aegis.core.api;

import java.util.HashSet;
import java.util.Set;

public interface AegisConfig {
    /**
     * 입력되는 basePackage의 포멧은 FQCN을 준수해야 한다. 예: com.ryc.domain
     *
     * @return
     */
    Set<String> basePackages();

    /**
     * 어플리케이션 메인 클래스를 기준으로 basePackage를 결정하는 경우 별도 초기화 없이 해당 메소드 호출
     *
     * @param primarySource : 어플리케이션 메인 클래스
     * @return
     */
    default Set<String> defaultBasePackages(Class<?> primarySource) {
        Set<String> defaults = new HashSet<>();

        String rootPackage = primarySource.getPackageName();
        defaults.add(rootPackage);

        return defaults;
    }
}
