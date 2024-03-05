package main.sulsul.beverage.domain;

import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum Category {
    ROOT("카테고리", null),
        BEER("맥주", ROOT),
        SOJU("소주", ROOT);

    private final String title;

    private final Category parentCategory;

    private final List<Category> childCategories;

    Category(String title, Category parentCategory) {
        this.childCategories = new ArrayList<>();
        this.title = title;
        this.parentCategory = parentCategory;
        if (Objects.nonNull(parentCategory)) {
            parentCategory.childCategories.add(this);
        }
    }

    public Optional<Category> getParentCategory() {
        return Optional.ofNullable(parentCategory);
    }

    public List<Category> getChildCategories() {
        return Collections.unmodifiableList(childCategories);
    }

    public boolean isLeafCategory() {
        return childCategories.isEmpty();
    }

    public List<Category> getLeafCategories() {
        return Arrays.stream(Category.values())
                .filter(Category::isLeafCategory)
                .collect(Collectors.toList());
    }

    private boolean isLeafCategoryOf(Category category) {
        return this.isLeafCategory() && category.contains(this);
    }

    private boolean contains(Category category) {
        if (this.equals(category)) return true;

        return Objects.nonNull(category.parentCategory) &&
                this.contains(category.parentCategory);
    }
}
