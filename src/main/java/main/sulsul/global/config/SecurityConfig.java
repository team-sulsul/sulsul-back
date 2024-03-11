package main.sulsul.global.config;

import lombok.extern.slf4j.Slf4j;
import main.sulsul.global.security.CustomAccessDeniedHandler;
import main.sulsul.global.security.FormAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new FormAuthenticationProvider(userDetailsService(), passwordEncoder());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
            .requestMatchers(PathRequest
                                 .toStaticResources()
                                 .atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authenticationProvider(authenticationProvider())
            .csrf(configurer -> configurer.ignoringRequestMatchers("/admin/**"))
            .formLogin(form -> {
                form.loginPage("/admin/login")
                    .defaultSuccessUrl("/admin/home")
                    .failureUrl("/admin/login?error=true")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/admin/login")
                    .successHandler((request, response, authentication) -> {
                        log.info("로그인 성공! {}", authentication);
                        response.sendRedirect("/admin/home");
                    });
            })
            .logout(logout -> {
                logout.logoutUrl("/admin/logout")
                    .logoutSuccessUrl("/admin/login");
            })
            .securityMatcher("/admin/**")
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/admin/login", "/admin/logout", "/admin/error").permitAll();
                auth.anyRequest().hasRole("ADMIN");
            })
            .exceptionHandling(configure -> {
                configure.accessDeniedHandler(new CustomAccessDeniedHandler("/admin/error"));
            })
            .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        log.info("인메모리 계정 등록");
        UserDetails normalUser = User.builder()
            .username("somefood")
            .password(passwordEncoder().encode("tjrwn12"))
            .roles("USER")
            .build();

        UserDetails adminUser = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("tjrwn12"))
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }
}
