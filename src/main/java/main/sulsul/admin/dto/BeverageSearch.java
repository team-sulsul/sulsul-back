package main.sulsul.admin.dto;

import lombok.Getter;
import lombok.Setter;
import main.sulsul.beverage.domain.Category;

@Getter
@Setter
public class BeverageSearch {

    private Category category;

    private String search;
}
