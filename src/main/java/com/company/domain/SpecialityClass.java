package com.company.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class SpecialityClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "speciality_class")
//    private Set<String> classes;
//

    public SpecialityClass() {
    }

    public SpecialityClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<String> getClasses() {
//        return classes;
//    }
//
//    public void setClasses(Set<String> classes) {
//        this.classes = classes;
//    }
}
