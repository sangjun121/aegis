package me.sangjun.aegis.core.sample;

import java.util.List;
import me.sangjun.aegis.core.api.DomainValidator;

public class LectureValidator implements DomainValidator<Lecture> {
    @Override
    public void validate(Lecture target) {
        validateId(target.getId());
        validateName(target.getName());
        validateProfessorName(target.getProfessorName());
        validateBlackListStudents(target.getBlackListStudents());
    }

    private void validateId(Long id) {
        // 내부 검증 로직 구현
    }

    private void validateName(String name) {
        // 내부 검증 로직 구현
    }

    private void validateProfessorName(String professorName) {
        // 내부 검증 로직 구현
    }

    private void validateStudents(List<String> students) {
        // 내부 검증 로직 구현
    }

    private void validateBlackListStudents(List<String> blackListStudents) {
        // 내부 검증 로직 구현
    }
}
