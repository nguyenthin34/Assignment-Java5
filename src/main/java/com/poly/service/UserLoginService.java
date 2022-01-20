package com.poly.service;

import com.poly.domain.Role;
import com.poly.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserLoginService implements UserDetailsService {
    @Autowired
    SessionService sessionService;

    @Autowired
    RoleService roleService;
    @Autowired
    UserService service;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.findById(username).get();
        Role role = roleService.findById(user.getRole().getRoleId()).get();

        //tạo userdetails từ account
        String password = user.getPassword();
        // tạo session
        if (user != null) {
            sessionService.set("username", username);
        } else {
            sessionService.set("username", null);
        }
        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(password)
                .roles(role.getName()).build();

    }
}
