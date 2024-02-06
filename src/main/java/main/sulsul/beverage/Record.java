package main.sulsul.beverage;

import jakarta.persistence.*;
import lombok.Getter;
import main.sulsul.global.domain.BaseEntity;
import main.sulsul.member.Member;

import java.time.LocalDate;

@Getter
@Entity
public class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate recordedAt;
}
