package main.sulsul.admin.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.sulsul.admin.dto.PageRequest;
import main.sulsul.admin.dto.PageResultDto;
import main.sulsul.admin.dto.RecordResponse;
import main.sulsul.member.domain.Member;
import main.sulsul.member.domain.dao.MemberRepository;
import main.sulsul.record.domain.Record;
import main.sulsul.record.domain.dao.RecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminMemberService {

    private final MemberRepository memberRepository;

    private final RecordRepository recordRepository;

    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));
    }

    public PageResultDto<Record, RecordResponse> getRecords(Long memberId, PageRequest pageRequest) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));

        final Pageable pageable = pageRequest.pageable();

        final Page<Record> recordPage = recordRepository.findAllByMemberId(member.getId(), pageable);
        final List<RecordResponse> list = recordPage.getContent()
            .stream()
            .map(record -> new RecordResponse(
                record.getId(),
                record.getRecordedAt(),
                record.getHangoverLevel().getLevel() + ", " + record.getHangoverLevel().getName(),
                record.getCreatedAt()
            ))
            .collect(Collectors.toList());

        return new PageResultDto<>(recordPage, list);
    }
}
