package main.sulsul.record.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.sulsul.beverage.domain.Beverage;
import main.sulsul.global.domain.BaseEntity;

@Getter
@NoArgsConstructor
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

    public RecordBeverage(Long recordId, Beverage beverage, Integer drink) {
        this.recordId = recordId;
        this.beverage = beverage;
        this.drink = drink;
    }
}
