package ee.fakeplastictrees.blog.category;

import ee.fakeplastictrees.blog.category.model.Category;
import ee.fakeplastictrees.blog.category.model.CategoryDto;

import static ee.fakeplastictrees.blog.core.Utils.builders;

public class CategoryMappers {
    public CategoryDto categoryToCategoryDto(Category category) {
        return builders().category().categoryDto()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return builders().category().category()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}
