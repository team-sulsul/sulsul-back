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

    private Long recordId;

    private Long beverageId;

    private Integer count; // 몇 잔
}
