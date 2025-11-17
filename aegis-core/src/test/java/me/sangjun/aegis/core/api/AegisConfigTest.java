package me.sangjun.aegis.core.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;

class AegisConfigTest {

    class DefaultAegisConfig implements AegisConfig {
        @Override
        public Set<String> basePackages() {
            return defaultBasePackages(AegisConfigTest.class);
        }
    }

    @Test
    void 메인클래스를_기반으로_basePackage가_올바르게_설정된다(){
        Set<String> thisClassPath = Set.of("me.sangjun.aegis.core.api");

        AegisConfig defaultAegisConfig = new DefaultAegisConfig();

        assertEquals(defaultAegisConfig.basePackages(), thisClassPath);
    }
}
