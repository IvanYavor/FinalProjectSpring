package com.company.controller;

import com.company.domain.User;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user,
                          BindingResult bindingResult,
                          Map<String, Object> model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        model.put("username", user.getUsername());


        if (!userService.addUser(user)) {
            model.put("message", "User exists");
            return "registration";
        }


        return "redirect:/login";
    }
}
