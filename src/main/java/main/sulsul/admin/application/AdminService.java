package main.sulsul.admin.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import main.sulsul.admin.dto.LoginRequest;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.dao.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void login(LoginRequest loginRequest) {
        Member foundMember = memberRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), foundMember.getPassword())) {
            throw new RuntimeException("invalid password");
        }

        HttpServletRequest requestAttributes = (HttpServletRequest) RequestContextHolder.getRequestAttributes();
        HttpSession session = requestAttributes.getSession();
        session.setAttribute("loginMember", foundMember);
        session.setMaxInactiveInterval(60 * 30);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member foundMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        List<GrantedAuthority> authorities = new ArrayList<>();

    }
}
