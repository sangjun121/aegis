package me.sangjun.aegis.sample;

import me.sangjun.aegis.core.api.AegisConfig;
import me.sangjun.aegis.core.bootstrap.AegisBootStrap;
import me.sangjun.aegis.sample.config.SampleAegisConfig;

public class SampleApplication {
    public static void main(String[] args) {
        AegisConfig config = new SampleAegisConfig();
        AegisBootStrap.run(SampleApplication.class, config);
    }
}
