package com.b511.drink.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/service/main")
    public String main(Model model){
        model.addAttribute("name", "glory");
        return "service/main";
    }
}
