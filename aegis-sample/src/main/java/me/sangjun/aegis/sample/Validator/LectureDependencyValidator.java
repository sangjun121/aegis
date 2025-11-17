package me.sangjun.aegis.sample.Validator;

import me.sangjun.aegis.core.api.DependencyValidator;
import me.sangjun.aegis.sample.domain.Lecture;
import me.sangjun.aegis.sample.domain.Member;

public class LectureDependencyValidator implements DependencyValidator<Lecture, Member> {
    @Override
    public void validate(Lecture lecture, Member member) {
        if (lecture.getBlackListStudents().contains(member.getName())) {
            // 예외발생
        }
    }
}
