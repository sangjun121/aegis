package me.sangjun.aegis.core.api;

import java.util.Collections;
import java.util.List;

public interface AegisConfig {
    List<String> basePackages();

    /**
     * 어플리케이션 메인 클래스를 기준으로 basePackage를 결정하는 경우 별도 초기화 없이 해당 메소드 호출
     * @param primarySource : 어플리케이션 메인 클래스
     * @return
     */
    default List<String> defaultBasePackages(Class<?> primarySource) {
        String rootPackage = primarySource.getPackageName();
        return Collections.singletonList(rootPackage);
    }
}
