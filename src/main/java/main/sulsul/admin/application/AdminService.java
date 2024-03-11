package main.sulsul.admin.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.dao.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminService {

    private final MemberRepository memberRepository;


    public List<Member> getAdminList() {
        return memberRepository.findAll();
    }

    public Member getAdmin(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));
    }
}
