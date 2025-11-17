package me.sangjun.aegis.core.scanner;

import static me.sangjun.aegis.core.exception.AegisErrorCode.CLASS_NOT_FOUND;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.util.HashSet;
import java.util.Set;
import me.sangjun.aegis.core.api.DomainValidator;
import me.sangjun.aegis.core.exception.AegisException;

public class ValidatorScanner {

    /**
     * scanValidator는 라이브러리를 제외한 순수 구현으로 작성.
     */
    public Set<Class<?>> scan(Class<?> primarySource, Set<String> basePackages, Class<?> targetInterface) {
        Set<Class<?>> validators = new HashSet<>();

        ClassLoader loader = primarySource.getClassLoader();

        ClassGraph classGraph = new ClassGraph()
                .enableClassInfo()
                .acceptPackages(basePackages.toArray(new String[0]));

        try (ScanResult scanResult = classGraph.scan()) {
            ClassInfoList validatorClassInfos =
                    scanResult.getClassesImplementing(targetInterface.getName());

            for (ClassInfo classInfo : validatorClassInfos) {
                String className = classInfo.getName();

                Class<?> clazz = Class.forName(className, false, loader);
                validators.add(clazz);
            }
        } catch (ClassNotFoundException e) {
            throw new AegisException(CLASS_NOT_FOUND);
        }

        return validators;
    }

    /**
     * @deprecated 내부 스캐닝 로직에 라이브러리를 도입하여 더 이상 사용되지 않습니다. {@link ValidatorScanner} scan에 구현된 ClassGraph 기반 스캐너를 사용.
     */
    @Deprecated
    private boolean isDomainValidator(Class<?> cls) {
        if (cls.isInterface()) {
            return false;
        }

        return DomainValidator.class.isAssignableFrom(cls);
    }
}
