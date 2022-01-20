package com.poly.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import com.poly.domain.Role;
import com.poly.domain.User;
import com.poly.dto.CategoriesDTO;
import com.poly.dto.RolesDTO;
import com.poly.dto.UsersDTO;
import com.poly.dto.UsersDTO2;
import com.poly.service.RoleService;
import com.poly.service.SessionService;
import com.poly.service.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/users")
public class UsersController {
    @Autowired
    UserService service;
    @Autowired
    SessionService sessionService;

    @Autowired
    RoleService roleService;
    @ModelAttribute("roles")
    public List<RolesDTO> getRoles(){
        return roleService.findAll().stream().map(item -> {
            RolesDTO dto = new RolesDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).toList();
    }
    @GetMapping("add")
    public String add(Model model) {
        UsersDTO dto = new UsersDTO();
        model.addAttribute("user", dto);
        return "/admin/user/save";
    }

    @PostMapping("save")
    public String saveOrupdate(RedirectAttributes params,
            ModelMap model,
            @Valid @ModelAttribute("user") UsersDTO dto,
            BindingResult bResult) {
        if (bResult.hasErrors()) {
            model.addAttribute("messaege", "Save Errors");
            return "/admin/user/save";
        }
        Optional<User> checkUser = service.findById(dto.getUsername());
        User checkEmail = service.findByEmailContaining(dto.getEmail());
        Optional<Role> role = roleService.findById(dto.getRole());
        if(checkUser.isPresent()){
            params.addAttribute("message", "User already exists");
            return "redirect:/admin/users/add";
        }else if(checkEmail != null){
            model.addAttribute("user", dto);
            model.addAttribute("message", "Email already exists");
            return "/admin/user/save";
        }else if(role.isEmpty()){
            model.addAttribute("user", dto);
            model.addAttribute("message", "Role already exists");
            return "/admin/user/edit";
        }
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        entity.setRole(role.get());
        service.save(entity);
        params.addAttribute("message", "Save is Success");
        return "redirect:/admin/users/add";
    }
    @PostMapping("update")
    public String update(RedirectAttributes params,ModelMap model,
                               @Valid @ModelAttribute("user") UsersDTO2 dto,
                               BindingResult bResult) {
        if (bResult.hasErrors()) {
            model.addAttribute("messaege", "Update Errors");
            return "/admin/user/edit";
        }
        User checkEmail = service.findByEmailContaining(dto.getEmail());
        Optional<User> checkUser = service.findById(dto.getUsername());
        Optional<Role> role = roleService.findById(dto.getRole());
       if(!dto.getEmail().equals(checkUser.get().getEmail()) && checkEmail != null){
            model.addAttribute("user", dto);
            model.addAttribute("message", "Email already exists");
            return "/admin/user/edit";
        }else if(role.isEmpty()){
           model.addAttribute("user", dto);
           model.addAttribute("message", "Role already exists");
           return "/admin/user/edit";
       }
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        entity.setRole(role.get());
        service.save(entity);
        model.addAttribute("message", "Update is Success");
        return "/admin/user/edit";
    }
    @GetMapping("")
    public String list(Model model,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        Pageable pageableDefault = PageRequest.of(0, 5, Sort.by("fullname"));

        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("fullname"));
        Page<User> resultPage = null;
        Page<User> resultPageDefault = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByFullnameContaining(name, pageable);
            resultPageDefault = service.findByFullnameContaining(name, pageableDefault);
        } else {
            resultPage = service.findAll(pageable);
            resultPageDefault = service.findAll(pageableDefault);
        }
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("userPage", resultPage);
        model.addAttribute("users", resultPageDefault.getContent());
        return "/admin/user/list";
    }

    @GetMapping("edit/{userId}")
    public ModelAndView edit(@PathVariable("userId") String userId,
            ModelMap model, RedirectAttributes params) {
        UsersDTO dto = new UsersDTO();
        Optional<User> optional = service.findById(userId);
        if (optional.isPresent()) {
            User entity = optional.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setPassword("");
            dto.setIsEdit(true);
            dto.setRole(entity.getRole().getRoleId());
            model.addAttribute("user", dto);
            return new ModelAndView("/admin/user/edit", model);
        }
        params.addAttribute("message", "user is not exists");
        return new ModelAndView("redirect:/admin/users", model);
    }

    @GetMapping("setStatus/{userId}")
    public String setStatus(@PathVariable("userId") String userId,
            ModelMap model, RedirectAttributes params) {
        UsersDTO dto = new UsersDTO();
        Optional<User> optional = service.findById(userId);
        if (optional.isPresent()) {
            User entity = optional.get();
            if (entity.getStatus()) {
                entity.setStatus(false);
            } else {
                entity.setStatus(true);
            }
            service.save(entity);
        } else {
            params.addAttribute("message", "Entity is not found");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("paginated")
    public String search(Model model,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("fullname"));
        Page<User> resultPage = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByFullnameContaining(name, pageable);
        } else {
            resultPage = service.findAll(pageable);
        }
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("userPage", resultPage);
        return "/admin/user/list";
    }
}
