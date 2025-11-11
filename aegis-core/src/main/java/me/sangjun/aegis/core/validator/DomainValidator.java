package me.sangjun.aegis.core.validator;

public interface DomainValidator<T> {
    void validate(T target);
}
