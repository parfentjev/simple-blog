package ee.fakeplastictrees.blog.post.controller;

import ee.fakeplastictrees.blog.core.annotation.AuthorizedEditor;
import ee.fakeplastictrees.blog.post.controller.request.PostPostsCategoriesRequest;
import ee.fakeplastictrees.blog.post.controller.request.PutPostsCategoriesRequest;
import ee.fakeplastictrees.blog.post.model.CategoryDto;
import ee.fakeplastictrees.blog.post.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CategoryDto> postCategories(@Valid @RequestBody PostPostsCategoriesRequest request) {
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedEditor
    public ResponseEntity<CategoryDto> putCategories(@PathVariable("categoryId") String categoryId, @Valid @RequestBody PutPostsCategoriesRequest request) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{categoryId}")
    @AuthorizedEditor
    public ResponseEntity<CategoryDto> deleteCategories(@PathVariable("categoryId") String categoryId) {
        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
