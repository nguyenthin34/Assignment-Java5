package com.poly.controller.admin;

import com.poly.domain.User;
import com.poly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    HttpSession session;

    @Autowired
    UserService service;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/home/login")
    public ModelAndView login(ModelMap model){
        return new ModelAndView("/site/login", model);
    }

    @GetMapping("/auth/login/success")
    public String success(RedirectAttributes params){
        String username = session.getAttribute("username").toString();
        User user = service.findById(username).get();
        if(user.getRole().getName().equals("ADMIN") || user.getRole().getName().equals("EMPLOYER") ){
            return "redirect:/home/index";
        }else{
            return "redirect:/home-shop-poly";
        }
    }
    @GetMapping("/auth/login/failed")
    public String failed(RedirectAttributes params, ModelMap model){
        params.addAttribute("message", "Login Failed");
        return "redirect:/home/login";
    }

    @GetMapping("/auth/logout/success")
    public String logout(){
            session.setAttribute("username", null);
            return "redirect:/home-shop-poly";
    }
    @GetMapping("/auth/access/denied")
    public String fai(RedirectAttributes params){
        String username = session.getAttribute("username").toString();
        User user = service.findById(username).get();
        if(user.getRole().getName().equals("ADMIN") || user.getRole().getName().equals("EMPLOYER") ){
            params.addAttribute("message", "You can't Login here");
            return "redirect:/home/index";
        }else{
            params.addAttribute("message", "You can't Login here");
            return "redirect:/home-shop-poly";
        }
    }
}
