package ee.fakeplastictrees.blog.category.service;

import ee.fakeplastictrees.blog.core.exceptions.ResourceNotFoundException;
import ee.fakeplastictrees.blog.category.model.Category;
import ee.fakeplastictrees.blog.category.model.CategoryDto;
import ee.fakeplastictrees.blog.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static ee.fakeplastictrees.blog.core.Utils.mappers;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(i -> mappers().category().categoryToCategoryDto(i))
                .collect(Collectors.toList());
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        return mappers().category().categoryToCategoryDto(categoryRepository.save(mappers().category().categoryDtoToCategory(categoryDto)));
    }

    public CategoryDto updateCategory(CategoryDto categoryDto) {
        if (!categoryRepository.existsById(categoryDto.getId())) {
            throw new ResourceNotFoundException(Category.class, categoryDto.getId());
        }

        return mappers().category().categoryToCategoryDto(categoryRepository.save(mappers().category().categoryDtoToCategory(categoryDto)));
    }

    public void deleteCategory(String categoryId) {
        categoryRepository.findById(categoryId).ifPresentOrElse(category -> categoryRepository.delete(category), () -> {
            throw new ResourceNotFoundException(Category.class, categoryId);
        });
    }
}
