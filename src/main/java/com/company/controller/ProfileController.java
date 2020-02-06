package com.company.controller;

import com.company.domain.ClassLessonsForChemist;
import com.company.domain.Role;
import com.company.domain.Specialty;
import com.company.domain.User;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Resource(name = "userService")
    private UserService userService;


    @GetMapping
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("admin", Role.ADMIN);
        model.addAttribute("specialities", Specialty.values());
        model.addAttribute("scoresMap", user.getSpecialityClass().getClassNameScoreMap());

        return "profile";
    }

//    @PostMapping
//    public String submitClassForm(@RequestParam String speciality,
//                                  @AuthenticationPrincipal User user) {
//        userService.saveSpeciality(speciality, user);
//
//        return "redirect:/profile";
//    }


}
