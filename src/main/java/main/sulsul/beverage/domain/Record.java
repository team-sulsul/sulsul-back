package main.sulsul.beverage.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.sulsul.global.domain.BaseEntity;
import main.sulsul.member.domain.Member;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate recordedAt;

    public Record(Member member, LocalDate recordedAt) {
        this.member = member;
        this.recordedAt = recordedAt;
    }
}
