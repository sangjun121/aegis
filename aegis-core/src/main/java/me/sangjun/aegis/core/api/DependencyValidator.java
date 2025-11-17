package me.sangjun.aegis.core.api;

public interface DependencyValidator<S, D> {
    void validate(S source, D dependency);
}
