package me.sangjun.aegis.core.binder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.sangjun.aegis.core.sample.Lecture;
import me.sangjun.aegis.core.sample.LectureDependencyValidator;
import me.sangjun.aegis.core.sample.LectureValidator;
import me.sangjun.aegis.core.sample.Member;
import me.sangjun.aegis.core.sample.MemberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DomainValidatorBinderTest {
    private DomainValidatorBinder domainValidatorBinder;

    private Set<Class<?>> validDomains;
    private Set<Class<?>> validValidators;

    @BeforeEach
    void setUp() {
        domainValidatorBinder = new DomainValidatorBinder();
        validDomains = Set.of(Member.class, Lecture.class);
        validValidators = Set.of(MemberValidator.class, LectureValidator.class);
    }

    @Test
    void 독립_단일_검증에_대해_올바르게_도메인과_검증기가_매핑된다() {
        Map<Class<?>, Class<?>> expected = new HashMap<>();
        expected.put(Lecture.class, LectureValidator.class);
        expected.put(Member.class, MemberValidator.class);

        Map<Class<?>, Class<?>> result = domainValidatorBinder.bind(validDomains, validValidators);

        assertEquals(expected, result);
    }

    @Test
    void 의존_검증에_대해_올바르게_소스도메인과_의존도메인이_매핑된다() {
        Map<Class<?>, List<Class<?>>> dependencyDomains = Map.of(
                Lecture.class, List.of(Member.class)
        );
        Set<Class<?>> validators = Set.of(LectureDependencyValidator.class);

        domainValidatorBinder.bindDependency(dependencyDomains, validators);
    }
}
