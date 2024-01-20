package org.example.hacking02_sk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {
    @RequestMapping("jsp/{path}")
    String path(@PathVariable String path, HttpServletRequest request, HttpServletResponse response) {
        return "jsp/" + path;
    }
}
