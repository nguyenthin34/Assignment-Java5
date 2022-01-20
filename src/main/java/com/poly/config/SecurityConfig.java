//package com.poly.config;
//
//import com.poly.service.SiteLoginService;
//import com.poly.service.UserLoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//
//@EnableWebSecurity
//public class SecurityConfig {
//    @Configuration
//    @Order(1)
//    public static class AuthConfig extends WebSecurityConfigurerAdapter {
//        @Autowired
//        UserLoginService userLoginService;
//
//        @Override
//        //Quản lý dữ liệu người dùng
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.userDetailsService(userLoginService);
//        }
//        //Phân quyền và hình thức đăng nhập
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            // CSRF,CORS
//            http.csrf().disable().cors().disable();
//            // Phân quyền sử dụng
//            http.authorizeRequests()
//                    .antMatchers("/admin/categories/**",
//                            "/admin/colors/**",
//                            "/admin/sizes/**",
//                            "/admin/customer/**",
//                            "/admin/providers/**",
//                            "/admin/statuses/**",
//                            "/admin/commodities/**",
//                            "/admin/profile/**"
//                    ).hasAnyRole("TRUE","FALSE")
//                    .antMatchers("/admin/**").hasRole("TRUE")
//                    .antMatchers("/home/index",
//                            "/auth/login/**",
//                            "/home/login",
//                            "/css/**",
//                            "/js/**",
//                            "/forgot/**").permitAll()
//                    .anyRequest().permitAll();
//            http.exceptionHandling().accessDeniedPage("/auth/access/denied");
//            //Giao diện đăng nhập
//            http.formLogin()
//                    .loginPage("/home/login")
//                    .loginProcessingUrl("/auth/login")
//                    .defaultSuccessUrl("/auth/login/success", false)
//                    .failureUrl("/auth/login/failed")
//                    .usernameParameter("username")
//                    .passwordParameter("password");
//
//            http.rememberMe()
//                    .rememberMeParameter("remember");
//
//            http.logout()
//                    .logoutUrl("/auth/logout")
//                    .logoutSuccessUrl("/auth/logout/success")
//                    .addLogoutHandler(new SecurityContextLogoutHandler());
//        }
//
//        @Bean
//        public PersistentTokenRepository persistentTokenRepository(){
//            InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl();
//            return memory;
//        }
//    }
//
//    @Configuration
//    @Order(2)
//    public static class SiteConfig extends WebSecurityConfigurerAdapter {
//        @Autowired
//        SiteLoginService siteLoginService;
//
//        @Autowired
//        BCryptPasswordEncoder passwordEncoder;
//        @Override
//        //Quản lý dữ liệu người dùng
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.userDetailsService(siteLoginService);
//        }
//        //Phân quyền và hình thức đăng nhập
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
////        @Bean
////        public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getRepository(){
////            return new HttpSessionOAuth2AuthorizationRequestRepository();
////        }
////        @Bean
////        public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken(){
////            return new DefaultAuthorizationCodeTokenResponseClient();
////        }
//            // CSRF,CORS
//            http.csrf().disable().cors().disable();
//            // Phân quyền sử dụng
////            http.authorizeRequests()
////                    .antMatchers("/admin/categories/**",
////                            "/admin/colors/**",
////                            "/admin/sizes/**",
////                            "/admin/customer/**",
////                            "/admin/providers/**",
////                            "/admin/statuses/**",
////                            "/admin/commodities/**",
////                            "/admin/profile/**"
////                    ).hasAnyRole("GUEST")
////                    .anyRequest().permitAll();
////            http.exceptionHandling().accessDeniedPage("/site/access/denied");
//            //Giao diện đăng nhập
//            http.formLogin()
//                    .loginPage("/site-login")
//                    .loginProcessingUrl("/site/login")
//                    .defaultSuccessUrl("/site/login/success", false)
//                    .failureUrl("/site/login/failed")
//                    .usernameParameter("username")
//                    .passwordParameter("password");
//
//            http.rememberMe()
//                    .rememberMeParameter("remember");
//
//            http.logout()
//                    .logoutUrl("/site/logout")
//                    .logoutSuccessUrl("/site/logout/success")
//                    .addLogoutHandler(new SecurityContextLogoutHandler());
//        }
//
//    }
//}
