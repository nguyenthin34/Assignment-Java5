package com.poly.controller.admin;

import com.poly.domain.User;
import com.poly.dto.MailSenderDTO;
import com.poly.dto.PasswordDTO;
import com.poly.dto.ProfileDTO;
import com.poly.service.SessionService;
import com.poly.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("admin/profile")
public class ProfileController {
    @Autowired
    UserService service;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    SessionService sessionService;
    @Autowired
    HttpSession session;

    @GetMapping("information/{username}")
    public String getProfile(RedirectAttributes params, Model model,
            @PathVariable("username")Optional<String> username){
        if(username.isEmpty() || username  == null){
            params.addAttribute("message", "Username is Empty");
            return "/admin/profile/info";
        }
        Optional<User> user = service.findById(username.get());
        if(user.isEmpty()){
            params.addAttribute("message", "User is Empty");
        }else{
            model.addAttribute("user", user.get());
        }
        return "/admin/profile/info";
    }

    @PostMapping("update")
    public String update(RedirectAttributes params, ModelMap model,
                         @Valid @ModelAttribute("user") ProfileDTO dto,
                         BindingResult bResult) {
        if (bResult.hasErrors()) {
            params.addAttribute("messaege", "Update Errors");
            return "redirect:/home/index";
        }
        User checkEmail = service.findByEmailContaining(dto.getEmail());
        Optional<User> checkUser = service.findById(dto.getUsername());

        if(!dto.getEmail().equals(checkUser.get().getEmail()) && checkEmail != null){
            model.addAttribute("user", dto);
            params.addAttribute("message", "Email already exists");
            return "redirect:/profile/information/" + dto.getUsername();
        }
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        entity.setStatus(checkUser.get().getStatus());
        entity.setRole(checkUser.get().getRole());
        service.save(entity);
        params.addAttribute("message", "Update is Success");
        return "redirect:/admin/profile/information/" + dto.getUsername();
    }

    @GetMapping("change")
    public String change(Model model){
        PasswordDTO dto = new PasswordDTO();
        model.addAttribute("passwords", dto);
        return "/admin/profile/changepassword";
    }

    @PostMapping("setNewPassword")
    public String setNewPass(@Valid @ModelAttribute("passwords") PasswordDTO dto,
                             BindingResult resultl,
                             RedirectAttributes params,
                             Model model){
        if(resultl.hasErrors()){
            params.addAttribute("message", "Error! An error occurred. Please try again later");
            return "redirect:/admin/profile/change";
        }
        if(!dto.getNewPassword().equals(dto.getConfirmPassword())){
            model.addAttribute("passwords", dto);
            params.addAttribute("message", "Password does not match!");
            return "redirect:/admin/profile/change";
        }
        User user = service.findById(dto.getUsername()).get();
        if(user == null ){
            model.addAttribute("passwords", dto);
            params.addAttribute("message", "Account does not exist!");
            return "redirect:/admin/profile/change";
        }else if(!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())){
            model.addAttribute("passwords", dto);
            params.addAttribute("message", "Password does not exist!");
            return "redirect:/admin/profile/change";
        }
        else{
            user.setPassword(dto.getConfirmPassword());
            service.save(user);
            params.addAttribute("message", "Change password successfully!");
            model.addAttribute("message", "Change password successfully!");
            return "redirect:/admin/profile/change";

        }
    }

}
