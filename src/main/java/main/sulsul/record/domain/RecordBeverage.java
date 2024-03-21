package main.sulsul.record.domain;

import jakarta.persistence.*;
import lombok.Getter;
import main.sulsul.beverage.domain.Beverage;
import main.sulsul.global.domain.BaseEntity;

@Getter
@Entity
public class RecordBeverage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recordId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private Beverage beverage;

    private Integer drink; // 몇 잔
}
