package ee.fakeplastictrees.blog.category;

import ee.fakeplastictrees.blog.category.model.Category;
import ee.fakeplastictrees.blog.category.model.CategoryDto;

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class CategoryTestSupport {
    public static Category generateCategory() {
        return builders().category().category()
                .id(randomAlphanumeric(10))
                .name(randomAlphanumeric(10))
                .build();
    }

    public static CategoryDto generateCategoryDto() {
        return builders().category().categoryDto()
                .id(randomAlphanumeric(10))
                .name(randomAlphanumeric(10))
                .build();
    }
}
