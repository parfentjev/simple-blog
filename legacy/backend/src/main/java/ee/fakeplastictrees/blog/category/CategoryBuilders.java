package ee.fakeplastictrees.blog.category;

import ee.fakeplastictrees.blog.category.controller.request.CategoryRequestBuilders;
import ee.fakeplastictrees.blog.category.model.Category;
import ee.fakeplastictrees.blog.category.model.CategoryDto;

public class CategoryBuilders {
    public CategoryDto.CategoryDtoBuilder categoryDto() {
        return CategoryDto.builder();
    }

    public Category.CategoryBuilder category() {
        return Category.builder();
    }

    public CategoryRequestBuilders request() {
        return new CategoryRequestBuilders();
    }
}
