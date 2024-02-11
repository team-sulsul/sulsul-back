package main.sulsul.beverage.domain;

import jakarta.persistence.*;
import lombok.Getter;
import main.sulsul.global.domain.BaseEntity;

@Getter
@Entity
public class RecordBeverage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beverage_id")
    private Beverage beverage;

    private Integer count; // 몇 잔
}
