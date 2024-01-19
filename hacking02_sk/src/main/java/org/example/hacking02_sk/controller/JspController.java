package org.example.hacking02_sk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JspController {
    @GetMapping("jspFile")
    String goJSP(){
        return "jsp/a";
    }
}
