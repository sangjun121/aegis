package me.sangjun.aegis.core.scanner;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.sangjun.aegis.core.annotations.AegisDomain;
import me.sangjun.aegis.core.api.AegisConfig;
import me.sangjun.aegis.core.util.ClassScanner;

public class DomainScanner {

    /**
     * AegisDomain 어노테이션을 기반으로만 도메인을 탐색하는 경우 진입점
     *
     * @param primarySource : 사용자 어플리케이션의 main 클래스이다.
     * @return 스캔된 도메인을 제공한다.
     */
    public static Set<Class<?>> scanDomain(Class<?> primarySource) {
        Set<Class<?>> domains = new HashSet<>();

        Path rootPath = ClassScanner.resolveClassesRoot(primarySource); // 1. classpath의 최상위 루트를 찾기
        List<Path> classFilePaths = ClassScanner.getClassFilePaths(rootPath); // 2. 루트 하위의 모든 .class 파일을 찾기

        for (Path path : classFilePaths) {
            String className = ClassScanner.parseClassName(rootPath, path); // 3. 클래스로더가 인식할 수 있는 클래스 네이밍으로 수정
            try {
                Class<?> actualClass = Class.forName(className); // 4. 클래스로더를 통해 클래스를 로드
                if (actualClass.isAnnotationPresent(AegisDomain.class)) { // 5. 어노테이션 탐지
                    domains.add(actualClass);
                }
            } catch (ClassNotFoundException e) {
                //TODO: 예외 로그 및 부트스트랩 실패
            }
        }

        return domains;
    }
}
