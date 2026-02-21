package com.club.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @GetMapping
    public String showIndexPage(Model model) {
        model.addAttribute("welcomeMessage", "환영합니다. V00 동아리 관리 시스템입니다");
        return "index";
    }

}
