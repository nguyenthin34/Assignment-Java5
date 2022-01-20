package com.poly.controller.admin;

import com.poly.domain.User;
import com.poly.dto.MailSenderDTO;
import com.poly.service.MailerService;
import com.poly.service.SessionService;
import com.poly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("forgot")
public class ForgotPassController {
    @Autowired
    UserService service;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    SessionService sessionService;
    @Autowired
    HttpSession session;
    @Autowired
    MailerService mailerService;
    MailSenderDTO mail(String emailTo, int code){
        MailSenderDTO dto = new MailSenderDTO();
        dto.setFrom("thintkhd28@gmail.com");
        dto.setTo(emailTo);
        dto.setBody(code + " is your ShopGiay verification code, please do not give any of this code to anyone");
        dto.setSubject("Mail to find the password of Shop Giay web site");
        return dto;
    }
    @GetMapping("")
    public String index(){
        return "/admin/profile/form";
    }
    Thread thread;
    int ranNum = 0;
    int count = 0;
    @GetMapping("ckeckUE")
    public String forgot(   @RequestParam("username") Optional<String> username,
                            @RequestParam("email") Optional<String> email,
                            RedirectAttributes params,
                            Model model) throws MessagingException {
        if(username.isEmpty() || email.isEmpty()){
            model.addAttribute("message", "Username or Email is Empty");
            return "/admin/profile/form";
        }
        Optional<User> user = service.findById(username.get());
        if(user.isEmpty()){
            model.addAttribute("message", "User is Empty");
            return "/admin/profile/form";
        }else if(!user.get().getEmail().equals(email.get())){
            model.addAttribute("message", "Email is Empty");
            return "/admin/profile/form";
        }else{
            ranNum = ThreadLocalRandom.current().nextInt(1000, 10000);
            session.setAttribute("code", ranNum);
            session.setAttribute("forgotUser", username.get());
            session.setAttribute("accuracy", false);
            session.setAttribute("email", email.get());
            mailerService.send(mail(email.get(), ranNum));
            count = 0;
            myMethod();
            thread.start();
            return "/admin/profile/checkCode";
        }
    }
    @GetMapping("requestCode")
    public String requestCode(RedirectAttributes params, Model model) throws MessagingException {
        String username = null;
        if(session.getAttribute("forgotUser") != null){
            username = session.getAttribute("forgotUser").toString();
        }else{
            params.addAttribute("message", "You have not verified your Account and Verification Code");
            return "redirect:/forgot";
        }
        thread.interrupt();
        count = 0;
        ranNum = 0;
        ranNum = ThreadLocalRandom.current().nextInt(1000, 10000);
        session.setAttribute("code", ranNum);
        mailerService.send(mail(session.getAttribute("email").toString(), ranNum));
        myMethod();
        thread.start();
        params.addAttribute("message", "Code sent back");
        return "/admin/profile/checkCode";
    }
    @PostMapping("accuracy")
    public String accuracy(RedirectAttributes params, Model model,
            @RequestParam("code") Optional<Integer> codeUser){
        session.setAttribute("code", ranNum);
        int code = Integer.valueOf(session.getAttribute("code").toString());
        if(codeUser.isEmpty()){
            params.addAttribute("message", "The code you entered does not exist");
            return "/admin/profile/checkCode";
        }
        if(code != codeUser.get()){
            params.addAttribute("message", "Code does not match");
            return "/admin/profile/checkCode";
        }else if(code == codeUser.get()){
            thread.interrupt();
            ranNum = 0;
            count = 0;
            session.setAttribute("code", null);
            session.setAttribute("accuracy", true);
            return "/admin/profile/newpass";
        }else{
            params.addAttribute("message", "Error! An error occurred. Please try again later");
            return "/admin/profile/checkCode";
        }
    }
    @PostMapping("setPass")
    public String setpass(@RequestParam("newPassword")Optional<String> newPassword,
                          @RequestParam("confirmPassword")Optional<String> confirmPassword,
                          Model model, RedirectAttributes params){
        boolean bln = Boolean.parseBoolean(session.getAttribute("accuracy").toString());
        String user = session.getAttribute("forgotUser").toString();

        if(!bln || user == null){
            params.addAttribute("message", "You have not verified your Account and Verification Code");
            return "redirect:/forgot";
        }
        if(newPassword.isEmpty() || confirmPassword.isEmpty()){
            model.addAttribute("message", "New Password or Confirm Password is empty");
            return "/admin/profile/newpass";
        }else if(!newPassword.get().equals(confirmPassword.get())){
            model.addAttribute("message", "Password does not match!");
            return "/admin/profile/newpass";
        }else{
            User entity = service.findById(user).get();
            entity.setPassword(confirmPassword.get());
            service.save(entity);
            params.addAttribute("message", "You have successfully changed your new password!");
            session.setAttribute("forgotUser", null);
            session.setAttribute("accuracy", null);
            return "redirect:/home/login";
        }
    }


    void myMethod() {
        thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    count++;
                    System.out.println(count);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                if (count == 30) {
                    count = 0;
                    ranNum = 0;
                }
            }
        };
    }
}
