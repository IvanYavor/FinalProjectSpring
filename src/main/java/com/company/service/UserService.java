package com.company.service;


import com.company.constant.Constant;
import com.company.domain.Role;
import com.company.domain.User;
import com.company.exception.RangeScoreException;
import com.company.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return true;
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }


    public void saveUser(User user, Map<String, String> form) throws RangeScoreException {
        Map<String, Integer> scores = new HashMap<>();
        for (String key : form.keySet()) {
            if (user.getSpecialityClass().getClassNameScoreMap().containsKey(key)) {
                scores.put(key, parseInt(form.get(key)));
            }
        }

        for (String key : scores.keySet()) {
            if (!checkRangeScore(scores.get(key))) {
                throw new RangeScoreException("Score is out of range");
            }
        }


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
        user.setAccepted(user.getSpecialityClass().isAccepted());

        userRepository.save(user);
    }


    private boolean checkRangeScore(Integer score) {
        return score >= Constant.MIN_SCORE && score <= Constant.MAX_SCORE;
    }
}
