package ee.fakeplastictrees.blog.post.service;

import ee.fakeplastictrees.blog.core.exceptions.ResourceAlreadyExistsException;
import ee.fakeplastictrees.blog.core.exceptions.ResourceNotFoundException;
import ee.fakeplastictrees.blog.post.controller.request.PostPostsCategoriesRequest;
import ee.fakeplastictrees.blog.post.controller.request.PutPostsCategoriesRequest;
import ee.fakeplastictrees.blog.post.model.Category;
import ee.fakeplastictrees.blog.post.model.CategoryDto;
import ee.fakeplastictrees.blog.post.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static ee.fakeplastictrees.blog.core.Utils.mappers;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(i -> mappers().post().categoryToCategoryDto(i))
                .collect(Collectors.toList());
    }

    public CategoryDto createCategory(PostPostsCategoriesRequest request) {
        verifyCategoryNameIsUnique(request.getName());

        return mappers().post().categoryToCategoryDto(categoryRepository.save(builders().post().category()
                .name(request.getName())
                .build()));
    }

    public CategoryDto updateCategory(String id, PutPostsCategoriesRequest request) {
        AtomicReference<CategoryDto> categoryReference = new AtomicReference<>();

        categoryRepository.findById(id).ifPresentOrElse(category -> {
            verifyCategoryNameIsUnique(request.getName());

            categoryReference.set(mappers().post().categoryToCategoryDto(categoryRepository.save(builders().post().category()
                    .id(id)
                    .name(request.getName())
                    .build())));
        }, () -> {
            throw new ResourceNotFoundException(Category.class, id);
        });

        return categoryReference.get();
    }

    public void deleteCategory(String id) {
        categoryRepository.findById(id).ifPresentOrElse(category -> categoryRepository.delete(category), () -> {
            throw new ResourceNotFoundException(Category.class, id);
        });
    }

    private void verifyCategoryNameIsUnique(String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new ResourceAlreadyExistsException(Category.class, name);
        }
    }
}
