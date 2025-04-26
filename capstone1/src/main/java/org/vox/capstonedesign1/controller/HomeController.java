package org.vox.capstonedesign1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/main")
    public String home() {
        return "Welcome to Vox";
    }
}
