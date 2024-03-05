package main.sulsul.beverage.domain;


import jakarta.persistence.*;
import lombok.Getter;
import main.sulsul.admin.dto.BeverageEditForm;
import main.sulsul.global.domain.BaseEntity;

@Getter
@Entity
public class Beverage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer capacity;

    @Column(columnDefinition = "VARCHAR(255)")
    @Enumerated(EnumType.STRING)
    private Category category;

    public void update(BeverageEditForm editForm) {
        this.name = editForm.getName();
        this.capacity = editForm.getCapacity();
        this.category = editForm.getCategory();
    }
}
