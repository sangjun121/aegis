package me.sangjun.aegis.core.sample;

import me.sangjun.aegis.core.api.DependencyValidator;

public class LectureDependencyValidator implements DependencyValidator<Lecture, Member> {

    @Override
    public void validate(Lecture lecture, Member member) {
        if(lecture.getBlackListStudents().contains(member.getName())) {
            // 예외발생
        }
    }
}
