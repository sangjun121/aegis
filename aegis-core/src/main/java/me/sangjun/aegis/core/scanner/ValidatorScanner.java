package me.sangjun.aegis.core.scanner;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.sangjun.aegis.core.api.DomainValidator;
import me.sangjun.aegis.core.util.ClassScanner;

public class ValidatorScanner {

    public static Set<Class<?>> scanValidator(Class<?> primarySource) {
        Set<Class<?>> validators = new HashSet<>();

        Path rootPath = ClassScanner.resolveClassesRoot(primarySource);
        List<Path> classFilePaths = ClassScanner.getClassFilePaths(rootPath);

        for (Path path : classFilePaths) {
            String className = ClassScanner.parseClassName(rootPath, path);
            try {
                Class<?> actualClass = Class.forName(className);
                if (isDomainValidator(actualClass)) {
                    validators.add(actualClass);
                }
            } catch (ClassNotFoundException e) {
                //TODO: 예외 로그 및 부트스트랩 실패
            }
        }

        return validators;
    }

    private static boolean isDomainValidator(Class<?> cls) {
        // 인터페이스 자체는 검색 대상에서 제거
        if (cls.isInterface()) {
            return false;
        }

        return DomainValidator.class.isAssignableFrom(cls);
    }
}
