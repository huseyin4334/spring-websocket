package org.example.springwebsocket.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class HomePageController {

    @GetMapping("/home")
    public String home() {
        log.info("Home page requested.");
        return "home";
    }
}
