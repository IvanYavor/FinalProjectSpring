package com.company.controller;

import com.company.domain.Role;
import com.company.domain.Specialty;
import com.company.domain.User;
import com.company.service.SpecialityService;
import com.company.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reg-speciality")
public class RegistrationSpecialityController {
    private final UserService userService;

    private final SpecialityService specialityService;

    public RegistrationSpecialityController(UserService userService, SpecialityService specialityService) {
        this.userService = userService;
        this.specialityService = specialityService;
    }

    @GetMapping
    public String getSpecialityForm(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("specialities", Specialty.values());
        model.addAttribute("admin", Role.ADMIN);

        return "specialityFormReg";
    }


    @PostMapping
    public String submitClassForm(@RequestParam String speciality,
                                  @AuthenticationPrincipal User user) {
        specialityService.saveSpeciality(speciality, user);

        return "redirect:/profile";
    }
}
