package com.company.controller;


import com.company.domain.Student;
import com.company.domain.User;
import com.company.repository.StudentRepository;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping("/")
    public String greeting(Map<String, Object> model) {

        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<User> users = userRepository.findAll();

        model.put("users", users);

        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String name, @RequestParam String faculty,  @RequestParam String grouping,  Map<String, Object> model) {
        Student student =  new Student(name, faculty, grouping);

        studentRepository.save(student);

        Iterable<Student> students = studentRepository.findAll();

        model.put("students", students);

        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Student> students;

        if(filter != null && !filter.isEmpty()) {
            students = studentRepository.findByGrouping(filter);
        } else {
            students = studentRepository.findAll();
        }

        model.put("students", students);

        return "main";
    }

}
