package com.chaisheng6.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutmeShowController {

    @GetMapping("/aboutme")
    public String aboutme(){
        return "aboutme";
    }
}
