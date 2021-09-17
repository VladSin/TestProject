package com.example.testProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/swagger/ui/")
public class RootController {

    @GetMapping("")
    public String swaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}
