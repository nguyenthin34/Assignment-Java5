package com.poly.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.poly.domain.Category;
import com.poly.domain.Role;
import com.poly.dto.RolesDTO;
import com.poly.service.CategoryService;

import com.poly.service.RoleService;
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
@RequestMapping("admin/roles")
public class RoleController{
    @Autowired
    RoleService service;
    
    @Autowired
    HttpSession session;
    @GetMapping("add")
    public String add(Model model) {
        RolesDTO dto = new RolesDTO();
        dto.setIsEdit(false);
        model.addAttribute("role", dto);
        return "/admin/role/addOredit";
    }

    @PostMapping("saveOrupdate")
    public String saveOrupdate(RedirectAttributes params,
                               @Valid @ModelAttribute("role") RolesDTO dto,
                               BindingResult bResult) {
        if (bResult.hasErrors()) {
            return "/admin/role/addOredit";
        }
        Role entity = new Role();
        BeanUtils.copyProperties(dto, entity);
        service.save(entity);
        params.addAttribute("message", "Save is Success");
        return "redirect:/admin/roles/add";
    }

    @GetMapping("")
    public String list(Model model,
                       @RequestParam(name = "name", required = false) String name,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size) {
        Pageable pageableDefault = PageRequest.of(0, 5, Sort.by("name"));
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Role> resultPage = null;
        Page<Role> resultPageDefault = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByNameContaining(name, pageable);
            resultPageDefault = service.findByNameContaining(name, pageableDefault);
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
        model.addAttribute("rolePage", resultPage);
        model.addAttribute("roles", resultPageDefault.getContent());
        return "/admin/role/list";
    }

    @GetMapping("edit/{roleId}")
    public ModelAndView edit(@PathVariable("roleId") Long roleId,
                             ModelMap model, RedirectAttributes params) {
        RolesDTO dto = new RolesDTO();
        Optional<Role> optional = service.findById(roleId);
        if (optional.isPresent()) {
            Role entity = optional.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("role", dto);
            return new ModelAndView("/admin/role/addOredit", model);
        }
        params.addAttribute("message", "role is not exists");
        return new ModelAndView("redirect:/admin/roles", model);
    }

    @GetMapping("setStatus/{roleId}")
    public String setStatus(@PathVariable("roleId") Long roleId,
                            ModelMap model, RedirectAttributes params) {
        RolesDTO dto = new RolesDTO();
        Optional<Role> optional = service.findById(roleId);
        if (optional.isPresent()) {
            Role entity = optional.get();
            if (entity.getStatus()) {
                entity.setStatus(false);
            } else {
                entity.setStatus(true);
            }
            service.save(entity);
        } else {
            params.addAttribute("message", "Entity is not found");
        }
        return "redirect:/admin/roles";
    }

    @GetMapping("paginated")
    public String search(Model model,
                         @RequestParam(name = "name", required = false) String name,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Role> resultPage = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByNameContaining(name, pageable);
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
        model.addAttribute("rolePage", resultPage);
        return "/admin/role/list";
    }
}
