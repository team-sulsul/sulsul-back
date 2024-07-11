package main.sulsul.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.MemberDto;
import main.sulsul.member.domain.MypageInfo;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.record.domain.Record;
import main.sulsul.record.domain.RecordBeverage;
import main.sulsul.record.domain.dao.RecordBeverageRepository;
import main.sulsul.record.domain.dao.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final RecordBeverageRepository recordBeverageRepository;
    private final RecordRepository recordRepository;


    public MemberDto findByMemberId(Long memberId) {
        MemberDto memberDto = new MemberDto();
        Member member = memberRepository.findById(memberId)
                .orElseThrow();

        memberDto.setRefreshToken(member.getRefreshToken());
        memberDto.setId(memberId);
        memberDto.setRole(member.getRole());
        memberDto.setNickname(member.getNickname());
        memberDto.setUsername(member.getUsername());
        return memberDto;

    }

    public MypageInfo myPageInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow();
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

    @Transactional
    public void withdraw(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow();
        member.setUseYn("N");
    }
}
