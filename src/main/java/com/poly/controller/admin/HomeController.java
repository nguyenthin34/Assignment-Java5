package com.poly.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/home/index")
    public String index(Model model){
        model.addAttribute("message", "This is home page");
        return "/admin/home";
    }
//    @GetMapping("login")
//    public String login(){
//        return "/admin/login/login";
//    }
}
