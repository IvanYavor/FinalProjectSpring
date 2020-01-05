package com.company.domain;

import javax.persistence.*;

@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String faculty;
    private String grouping;

    public Student() {

    }

    public Student(String name, String faculty, String group) {
        this.name = name;
        this.faculty = faculty;
        this.grouping = group;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String group) {
        this.grouping = group;
    }
}
