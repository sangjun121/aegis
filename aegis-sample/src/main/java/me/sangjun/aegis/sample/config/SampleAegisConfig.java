package me.sangjun.aegis.sample.config;

import java.util.Set;
import me.sangjun.aegis.core.api.AegisConfig;

public class SampleAegisConfig implements AegisConfig {
    @Override
    public Set<String> basePackages() {
        return Set.of("me.sangjun.aegis.sample");
    }
}
