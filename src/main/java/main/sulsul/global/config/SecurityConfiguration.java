package main.sulsul.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(configurer -> configurer.ignoringRequestMatchers("/admin/**"))
            .formLogin(form -> {
                form.loginPage("/admin/login")
                    .defaultSuccessUrl("/admin/home")
                    .failureUrl("/admin/login?error=true")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/admin/login");
            })
            .securityMatcher("/admin/**")
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/admin/login").permitAll();
                auth.anyRequest().authenticated();
            }).build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
            .username("somefood")
            .password(passwordEncoder().encode("tjrwn12"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}
