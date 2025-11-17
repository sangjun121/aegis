package me.sangjun.aegis.core.binder;

/**
 * 소스 도메인과 의존 도메인을 1대1 매핑하는 dto
 *
 * @param source
 * @param dependency
 */
public record DependencyKey(Class<?> source, Class<?> dependency) {
}
