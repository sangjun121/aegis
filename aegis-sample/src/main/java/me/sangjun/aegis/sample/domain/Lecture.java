package me.sangjun.aegis.sample.domain;

import java.util.List;
import me.sangjun.aegis.core.annotations.AegisDomain;
import me.sangjun.aegis.core.annotations.ValidationDependsOn;
import me.sangjun.aegis.sample.Validator.LectureDependencyValidator;
import me.sangjun.aegis.sample.Validator.LectureValidator;

@AegisDomain
@ValidationDependsOn(Member.class)
public class Lecture {
    private final Long id;
    private final String name;
    private final String professorName;
    private final List<String> students;
    private final List<String> blackListStudents;

    public Lecture(Long id, String name, String professorName,
                   List<String> students, List<String> blackListStudents) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.professorName = professorName;
        this.blackListStudents = blackListStudents;
        new LectureValidator().validate(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfessorName() {
        return professorName;
    }

    public List<String> getBlackListStudents() {
        return blackListStudents;
    }

    /**
     * 객체 생성시 외부 객체에 상태에 의존하는 경우
     *
     * @param student
     * @return
     */
    public Lecture register(Member student) {
        new LectureDependencyValidator().validate(this, student);

        students.add(student.getName());
        return this;
    }
}
