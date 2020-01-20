package com.company.domain;

import com.company.validation.ScoresConstraint;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity

public class SpecialityClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Specialty name;


    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "class_score_mapping",
            joinColumns = {@JoinColumn(name = "speciality_class_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "class_name")
    @Column(name = "score")
    private Map<String, Integer> classNameScoreMap = new HashMap<>();

    public SpecialityClass() {
    }

    public SpecialityClass(Specialty name, Map<String, Integer> scores) {

        this.name = name;
        this.classNameScoreMap = scores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Specialty getName() {
        return name;
    }

    public void setName(Specialty name) {
        this.name = name;
    }

    public Map<String, Integer> getClassNameScoreMap() {
        return classNameScoreMap;
    }

    public void setClassNameScoreMap(Map<String, Integer> classNameScoreMap) {
        this.classNameScoreMap = classNameScoreMap;
    }


}
