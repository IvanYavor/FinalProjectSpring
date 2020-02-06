package com.company.controller;


import com.company.service.SpecialityService;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rating")
public class RateController {
    private final SpecialityService specialityService;

    @Autowired
    public RateController(SpecialityService specialityService, UserService userService) {
        this.specialityService = specialityService;
    }

    @GetMapping
    public String getRating(Model model) {
        model.addAttribute("doctors", specialityService.getAllSortedDoctors());
        model.addAttribute("chemists", specialityService.getAllSortedChemists());
        return "rating";
    }
}
