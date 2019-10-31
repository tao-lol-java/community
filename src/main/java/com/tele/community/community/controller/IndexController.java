package com.tele.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 2019/10/30
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String hello(){
        return "index";
    }

}
