package me.sangjun.aegis.core.api;

public interface DomainValidator<T> {
    void validate(T target);
}
