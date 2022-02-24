package com.poly.config;

import com.poly.service.UserLoginService;
import com.poly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserLoginService userLoginService;

    @Override
    //Quản lý dữ liệu người dùng
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userLoginService);
    }
    //Phân quyền và hình thức đăng nhập
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        @Bean
//        public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getRepository(){
//            return new HttpSessionOAuth2AuthorizationRequestRepository();
//        }
//        @Bean
//        public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken(){
//            return new DefaultAuthorizationCodeTokenResponseClient();
//        }
        // CSRF,CORS
        http.csrf().disable().cors().disable();
        // Phân quyền sử dụng
        http.authorizeRequests()

                .antMatchers("/admin/categories/**",
                        "/admin/colors/**",
                        "/admin/sizes/**",
                        "/admin/providers/**",
                        "/admin/statuses/**",
                        "/admin/commodities/**",
                        "/admin/profile/**"
                        ).hasAnyRole("EMPLOYER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/home/index",
                        "/home/login",
                        "/css/**",
                        "/js/**",
                        "/forgot/**").permitAll();
        http.exceptionHandling().accessDeniedPage("/auth/access/denied");
        //Giao diện đăng nhập
        http.formLogin()
                .loginPage("/home/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/auth/login/success", false)
                .failureUrl("/auth/login/failed")
                .usernameParameter("username")// mặc định [username]
                .passwordParameter("password"); //mặc định [password]

        http.rememberMe()
                .rememberMeParameter("remember"); // mặc định [remember-me]

        http.logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/logout/success")
                .addLogoutHandler(new SecurityContextLogoutHandler());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl();
        return memory;
    }
}
