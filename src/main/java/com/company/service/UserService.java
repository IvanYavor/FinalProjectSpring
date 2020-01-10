package com.company.service;


import com.company.domain.*;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        form.keySet();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }


    public void saveClasses(String speciality, User user) {
        Map<String, Integer> scores = new HashMap<String, Integer>();


        if(speciality.equals(Specialty.DOCTOR.toString())) {
            for(ClassLessonsForDoctor s : ClassLessonsForDoctor.values()) {
                scores.put(s.toString(), 0);
            }

            user.setScores(scores);
        } else if (speciality.equals(Specialty.CHEMIST.toString())) {
            for(ClassLessonsForChemist s : ClassLessonsForChemist.values()) {
                scores.put(s.toString(), 0);
            }

            user.setScores(scores);
        }


    }

//    public void saveSpeciality(String speciality, User user) {
//        SpecialityClass specialityClass = new SpecialityClass(speciality);
//        Set<String> classes = new HashSet<>();
//
//        classes.add("BIOLOGY");
//        classes.add("ENGLISH");
//        classes.add("CHEMISTRY");
//
//        specialityClass.setClasses(classes);
//
//
//        user.setSpecialityClass(specialityClass);
//
//    }
}
