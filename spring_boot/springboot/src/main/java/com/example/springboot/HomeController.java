package com.example.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHome() {
        return "home";
    }

    // @GetMapping("/contact")
    // public String showContact() {
    //     return "contact";
    // }

//     @RequestMapping(value = "/contact", method = RequestMethod.GET)
// public String showContact() {
//     return "contact";}

@RequestMapping("/contact")
public String showContact() {
    return "contact";
}

}