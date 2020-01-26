package com.company.service;


import com.company.domain.*;
import com.company.exception.RangeScoreException;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(userFromDb != null) {
            return false;
        }

        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> getAllStudets() {
        List<User> students = userRepository.findAllStudents();

        return compareStudentScores(students);
    }

    private List<User> compareStudentScores(List<User> students) {
        List<User> doctors = userRepository.findAllDoctorStudents();
        List<User> chemists = userRepository.findAllChemistStudents();


        Comparator<User> comparatorByScores = new Comparator<User>() {
            @Override
            public int compare(User user, User t1) {
                Integer score1 = 0;
                if(user.getSpecialityClass() != null) {
                    for (String key : user.getSpecialityClass().getClassNameScoreMap().keySet()) {
                        score1 += user.getSpecialityClass().getClassNameScoreMap().get(key);
                    }
                }

                Integer score2 = 0;
                if(t1.getSpecialityClass() != null ) {
                    for (String key : t1.getSpecialityClass().getClassNameScoreMap().keySet()) {
                        score2 += t1.getSpecialityClass().getClassNameScoreMap().get(key);
                    }
                }
                return score2-score1;
            }
        };

        Collections.sort(doctors, comparatorByScores);
        Collections.sort(chemists, comparatorByScores);

        students = Stream.concat(
                doctors.stream(),
                chemists.stream()
        ).collect(Collectors.toList());

        return students;

    }

    public void saveUser(User user, String username, Map<String, String> form) throws RangeScoreException {
        Map<String, Integer> scores = new HashMap<>();
        for(String key: form.keySet()) {
            if(user.getSpecialityClass().getClassNameScoreMap().containsKey(key)) {
                scores.put(key, parseInt(form.get(key)));
            }
        }

        for(String key : scores.keySet()) {
            if(!checkRangeScore(scores.get(key))) {
                throw new RangeScoreException("Score is out of range");
            }
        }



        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();


        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }





        user.getSpecialityClass().setClassNameScoreMap(scores);

        user.setAccepted(checkIfAccepted(user));

        userRepository.save(user);
    }

    private boolean checkRangeScore(Integer score) {
        return score >= 0 && score <= 100;
    }

    private boolean checkIfAccepted(User user) {
        Map<String, Integer> scores = user.getSpecialityClass().getClassNameScoreMap();
        for(String key : scores.keySet()) {
            if(scores.get(key) < 70) {
                return false;
            }
        }
        return true;
    }


    public void saveSpeciality(String speciality, User user) {
        Map<String, Integer> scores = getClassesForSpeciality(speciality);

        for(Specialty s : Specialty.values()) {
            if(s.toString().equals(speciality)) {
                SpecialityClass c = new SpecialityClass(s, scores);
                user.setSpecialityClass(c);
            }
        }


        userRepository.save(user);
    }

    private Map<String, Integer> getClassesForSpeciality(String speciality) {
        Map<String, Integer> scores = new HashMap<>();
        if(speciality.equals(Specialty.DOCTOR.toString())) {
            for(ClassLessonsForDoctor c : ClassLessonsForDoctor.values()) {
                scores.put(c.toString(), 0);
            }
        } else if(speciality.equals(Specialty.CHEMIST.toString())) {
            for(ClassLessonsForChemist c : ClassLessonsForChemist.values()) {
                scores.put(c.toString(), 0);
            }
        }

        return scores;
    }
}
