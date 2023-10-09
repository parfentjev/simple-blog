package ee.fakeplastictrees.blog.category.controller;

import ee.fakeplastictrees.blog.core.annotation.AuthorizedEditor;
import ee.fakeplastictrees.blog.category.controller.request.PostCategoriesRequest;
import ee.fakeplastictrees.blog.category.controller.request.PutCategoriesRequest;
import ee.fakeplastictrees.blog.category.model.CategoryDto;
import ee.fakeplastictrees.blog.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ee.fakeplastictrees.blog.core.Utils.builders;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedEditor
    public ResponseEntity<CategoryDto> postCategories(@Valid @RequestBody PostCategoriesRequest request) {
        CategoryDto categoryDto = builders().category().categoryDto()
                .name(request.getName())
                .build();

        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedEditor
    public ResponseEntity<CategoryDto> putCategories(@PathVariable("categoryId") String categoryId, @Valid @RequestBody PutCategoriesRequest request) {
        CategoryDto categoryDto = builders().category().categoryDto()
                .id(categoryId)
                .name(request.getName())
                .build();

        return new ResponseEntity<>(categoryService.updateCategory(categoryDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{categoryId}")
    @AuthorizedEditor
    public ResponseEntity<Void> deleteCategories(@PathVariable("categoryId") String categoryId) {
        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
