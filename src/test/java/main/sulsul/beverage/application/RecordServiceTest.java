package main.sulsul.beverage.application;

import main.sulsul.beverage.domain.dao.RecordRepository;
import main.sulsul.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordService recordService;

    @Test
    @DisplayName("레코드 저장하기")
    void saveRecord() throws Exception {
        // given
//        Member member = new Member();
        LocalDate now = LocalDate.now();

        // when
//        recordService.createRecord(member, now);

        // then
    }
}
