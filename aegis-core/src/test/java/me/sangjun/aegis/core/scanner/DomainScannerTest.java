package me.sangjun.aegis.core.scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;
import me.sangjun.aegis.core.SamplePrimary;
import me.sangjun.aegis.core.annotations.AegisDomain;
import me.sangjun.aegis.core.api.AegisConfig;
import me.sangjun.aegis.core.api.AegisConfigTest.DefaultAegisConfig;
import me.sangjun.aegis.core.sample.Lecture;
import me.sangjun.aegis.core.sample.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DomainScannerTest {
    private AegisConfig aegisConfig;
    private DomainScanner domainScanner;

    @BeforeEach
    void setUp() {
        aegisConfig = new DefaultAegisConfig();
        domainScanner = new DomainScanner();
    }

    @Test
    void classPath_하위에_위치한_도메인을_정상적으로_스캔한다() {
        Set<Class<?>> expected = Set.of(Member.class, Lecture.class);

        Set<Class<?>> result =
                domainScanner.scan(SamplePrimary.class, aegisConfig.basePackages(), AegisDomain.class);

        assertEquals(expected, result);
    }

    @Test
    void classPath_하위에_위치한_의존_도메인을_정상적으로_스캔한다() {
        Set<Class<?>> domains = Set.of(Member.class, Lecture.class);

        Map<Class<?>, List<Class<?>>> expected = Map.of(
                Lecture.class, List.of(Member.class)
        );

        Map<Class<?>, List<Class<?>>> result = domainScanner.scanDependencyDomains(domains);

        assertEquals(expected, result);
    }
}
