package com.sens.pond.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("")
    public String home(){
        return "index";
    }

    @GetMapping("/show/input")
    public String input(){
        return "input";
    }

}
