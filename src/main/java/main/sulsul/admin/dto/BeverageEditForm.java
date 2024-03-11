package main.sulsul.admin.dto;

import lombok.Getter;
import lombok.Setter;
import main.sulsul.beverage.domain.Category;

@Getter
@Setter
public class BeverageEditForm {

    private String name;

    private Integer capacity;

    private Category category;
}
