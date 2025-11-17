package me.sangjun.aegis.core.scanner;

import java.util.Set;
import me.sangjun.aegis.core.SamplePrimary;
import me.sangjun.aegis.core.api.AegisConfig;
import me.sangjun.aegis.core.api.AegisConfigTest.DefaultAegisConfig;
import me.sangjun.aegis.core.api.DomainValidator;
import me.sangjun.aegis.core.sample.LectureValidator;
import me.sangjun.aegis.core.sample.MemberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorScannerTest {
    private AegisConfig aegisConfig;
    private ValidatorScanner validatorScanner;

    @BeforeEach
    public void setUp() {
        this.aegisConfig = new DefaultAegisConfig();
        this.validatorScanner = new ValidatorScanner();
    }

    @Test
    void 특정_라이브러리를_구현한_검증_클래스를_정상적으로_탐색한다() {
        Set<Class<?>> expected = Set.of(MemberValidator.class, LectureValidator.class);
        Set<Class<?>> result = validatorScanner.scan(SamplePrimary.class, aegisConfig.basePackages(),
                DomainValidator.class);

        assertEquals(expected, result);
    }

}
