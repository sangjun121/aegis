package me.sangjun.aegis.core.api;

import java.util.Collections;
import java.util.List;

public abstract class AegisConfig {
    public List<String> baseDomainPaths() {
        return Collections.emptyList();
    }

    public List<Class<?>> domains() {
        return Collections.emptyList();
    }
}
