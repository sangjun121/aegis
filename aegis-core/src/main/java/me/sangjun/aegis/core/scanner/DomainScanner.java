package me.sangjun.aegis.core.scanner;

import static me.sangjun.aegis.core.exception.AegisErrorMessage.CLASS_NOT_FOUND;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.util.HashSet;
import java.util.Set;
import me.sangjun.aegis.core.annotations.AegisDomain;
import me.sangjun.aegis.core.exception.AegisException;

public class DomainScanner {

    /**
     * AegisDomain 어노테이션을 기반으로만 도메인을 탐색하는 경우 진입점
     *
     * @param primarySource : 사용자 어플리케이션의 main 클래스이다.
     * @return 스캔된 도메인을 제공한다.
     */
    public Set<Class<?>> scan(Class<?> primarySource, Set<String> basePackages) {
        Set<Class<?>> domains = new HashSet<>();

        ClassLoader loader = primarySource.getClassLoader(); // 해당 ClassLoader는 단지 도메인을 저장하기 위해서만 사용.

        ClassGraph classGraph = new ClassGraph()
                .enableClassInfo() // 클래스 정보 수집
                .enableAnnotationInfo() // 클래스, 메서드, 필드에 달린 어노테이션 정보
                .acceptPackages(basePackages.toArray(new String[0])); // 스캔 범위를 basePackages 하위로 선정

        try (ScanResult scanResult = classGraph.scan()) {
            ClassInfoList domainClassInfos =
                    scanResult.getClassesWithAnnotation(AegisDomain.class.getName()); // @AegisDomain 붙은 클래스 가져오기

            for (ClassInfo classInfo : domainClassInfos) {
                String className = classInfo.getName();

                Class<?> clazz = Class.forName(className, false, loader); // 초기화를 제외한 로딩
                domains.add(clazz);
            }
        } catch (ClassNotFoundException e) {
            throw new AegisException(CLASS_NOT_FOUND.getMessage());
        }

        return domains;
    }
}
