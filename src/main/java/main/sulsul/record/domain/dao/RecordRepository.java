package main.sulsul.record.domain.dao;

import java.time.LocalDate;
import java.util.Optional;
import main.sulsul.record.domain.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordCustomRepository {

    Page<Record> findAllByMemberId(final Long memberId, Pageable pageable);

    Optional<Record> findByMemberIdAndRecordedAt(final Long memberId, final LocalDate recordedAt);
}
