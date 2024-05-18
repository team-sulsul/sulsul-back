package main.sulsul.member.application;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.MemberDto;
import main.sulsul.member.domain.MypageInfo;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.oauth.domain.token.JwtTokensGenerator;
import main.sulsul.record.domain.Record;
import main.sulsul.record.domain.RecordBeverage;
import main.sulsul.record.domain.dao.RecordBeverageRepository;
import main.sulsul.record.domain.dao.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokensGenerator jwtTokensGenerator;
    private final RecordBeverageRepository recordBeverageRepository;
    private final RecordRepository recordRepository;


    public MemberDto findByAccessToken(HttpServletRequest request) {
        MemberDto memberDto = new MemberDto();
        try {
            String accessToken = request.getHeader("Authorization").substring(7);
            Long memberId = jwtTokensGenerator.extractMemberId(accessToken);
            Member member = memberRepository.findById(memberId).get();
            memberDto.setRefreshToken(member.getRefreshToken());
            memberDto.setId(memberId);
            memberDto.setRole(member.getRole());
            memberDto.setNickname(member.getNickname());
            memberDto.setUsername(member.getUsername());
            return memberDto;
        } catch (IllegalArgumentException e) {
            memberDto.setMessage("유효하지 않은 회원입니다.");
            return memberDto;
        }
    }

    public MypageInfo myPageInfo(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        Long memberId = jwtTokensGenerator.extractMemberId(accessToken);
        Member member = memberRepository.findById(memberId).get();
        List<Record> records = recordRepository.findAllByMemberId(memberId);
        Integer total = 0;
        for (Record record : records) {
            Long id = record.getId();
            List<RecordBeverage> recordBeverages = recordBeverageRepository.findAllByRecordId(id);
            for (RecordBeverage r : recordBeverages) {
                Integer drink = r.getDrink();
                total += drink;
            }
        }
        MypageInfo myPageInfo = new MypageInfo();
        myPageInfo.setNickname(member.getNickname());
        myPageInfo.setDrink(total);
        return myPageInfo;
    }
}
