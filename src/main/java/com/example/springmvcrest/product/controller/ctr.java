package com.example.springmvcrest.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ctr {
    @RequestMapping("/operations")
    public String test() {

        return "index";
    }
}
