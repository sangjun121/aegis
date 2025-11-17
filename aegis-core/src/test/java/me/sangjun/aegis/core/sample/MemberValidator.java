package me.sangjun.aegis.core.sample;

import me.sangjun.aegis.core.api.DomainValidator;

public class MemberValidator implements DomainValidator<Member> {
    @Override
    public void validate(Member target) {
        validateId(target.getId());
        validateName(target.getName());
        validateEmail(target.getEmail());
        validatePassword(target.getPassword());
    }

    private void validateId(Long id) {
        // 내부 검증 로직 구현
    }

    private void validateName(String name) {
        // 내부 검증 로직 구현
    }

    private void validateEmail(String email) {
        // 내부 검증 로직 구현
    }

    private void validatePassword(String password) {
        // 내부 검증 로직 구현
    }
}
