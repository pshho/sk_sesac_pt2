package org.example.hacking02_sk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @RequestMapping("/")
    String index(@RequestParam(required = false) String name, Model model){
    	if (name != null) {
    		model.addAttribute("name", name);
    	}
        return "index";
    }
}
