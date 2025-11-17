package me.sangjun.aegis.core.alert;

import me.sangjun.aegis.core.SamplePrimary;
import me.sangjun.aegis.core.api.AegisConfig;
import me.sangjun.aegis.core.api.AegisConfigTest.DefaultAegisConfig;
import me.sangjun.aegis.core.bootstrap.AegisBootStrap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AegisInfoLoggerTest {
    private AegisConfig aegisConfig;

    @BeforeEach
    void setUp() {
        aegisConfig = new DefaultAegisConfig();
    }
    @Test
    public void test() {
        AegisBootStrap.run(SamplePrimary.class, aegisConfig);
    }
}
