package me.sangjun.aegis.core.api;

@Deprecated
public interface DependencyValidator<S, D> {
    void validate(S source, D dependency);
}
