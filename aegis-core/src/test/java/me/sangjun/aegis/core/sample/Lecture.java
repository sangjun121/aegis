package me.sangjun.aegis.core.sample;

import me.sangjun.aegis.core.annotations.AegisDomain;

@AegisDomain
public class Lecture {
    private final Long id;
    private final String name;
    private final String professorName;

    public Lecture(Long id, String name, String professorName) {
        this.id = id;
        this.name = name;
        this.professorName = professorName;
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
}
