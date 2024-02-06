package main.sulsul.beverage;


import jakarta.persistence.*;
import lombok.Getter;
import main.sulsul.global.domain.BaseEntity;

@Getter
@Entity
public class Beverage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer capacity;

    @Column(columnDefinition = "VARCHAR(255)")
    @Enumerated(EnumType.STRING)
    private Category category;
}
