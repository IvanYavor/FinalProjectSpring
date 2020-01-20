package com.company.controller;


import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rating")
public class RateContoroller {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getRating(Model model) {
        model.addAttribute("allStudents", userService.getAllStudets());
        return "rating";
    }
}
