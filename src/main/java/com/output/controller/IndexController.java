package com.output.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping({"/index","/"})
    public String index(HttpServletRequest request) {
        return "index";
    }
}
