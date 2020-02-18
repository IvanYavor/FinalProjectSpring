package com.company.service;

import com.company.domain.*;
import com.company.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecialityService {
    private final UserRepository userRepository;

    public SpecialityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllSortedDoctors() {
        return compareStudentScores(userRepository.findAllDoctorStudents());
    }

    public List<User> getAllSortedChemists() {
        return compareStudentScores(userRepository.findAllChemistStudents());
    }

    private List<User> compareStudentScores(List<User> students) {
        Comparator<User> comparatorByScores = (user, t1) -> {
            Integer score1 = 0;
            if (user.getSpecialityClass() != null) {
                for (String key : user.getSpecialityClass().getClassNameScoreMap().keySet()) {
                    score1 += user.getSpecialityClass().getClassNameScoreMap().get(key);
                }
            }

            Integer score2 = 0;
            if (t1.getSpecialityClass() != null) {
                for (String key : t1.getSpecialityClass().getClassNameScoreMap().keySet()) {
                    score2 += t1.getSpecialityClass().getClassNameScoreMap().get(key);
                }
            }
            return score2 - score1;
        };

        students.sort(comparatorByScores);

        return students;

    }

    public void saveSpeciality(String speciality, User user) {
        Map<String, Integer> scores = getClassesForSpeciality(speciality);

        for (Specialty s : Specialty.values()) {
            if (s.toString().equals(speciality)) {
                SpecialityClass c = new SpecialityClass(s, scores);
                user.setSpecialityClass(c);
            }
        }


        userRepository.save(user);
    }

    private Map<String, Integer> getClassesForSpeciality(String speciality) {
        Map<String, Integer> scores = new HashMap<>();
        if (speciality.equals(Specialty.DOCTOR.toString())) {
            for (ClassLessonsForDoctor c : ClassLessonsForDoctor.values()) {
                scores.put(c.toString(), 0);
            }
        } else if (speciality.equals(Specialty.CHEMIST.toString())) {
            for (ClassLessonsForChemist c : ClassLessonsForChemist.values()) {
                scores.put(c.toString(), 0);
            }
        }

        return scores;
    }

}
