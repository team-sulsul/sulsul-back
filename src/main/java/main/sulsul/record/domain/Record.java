package main.sulsul.record.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.sulsul.global.domain.BaseEntity;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private DrunkenLevel drunkenLevel;

    private LocalDate recordedAt;

    public Record(Long memberId, DrunkenLevel drunkenLevel, LocalDate recordedAt) {
        this.memberId = memberId;
        this.drunkenLevel = drunkenLevel;
        this.recordedAt = recordedAt;
    }
}
