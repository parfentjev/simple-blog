package ee.fakeplastictrees.blog.category;

import ee.fakeplastictrees.blog.category.model.Category;
import ee.fakeplastictrees.blog.category.model.CategoryDto;
import ee.fakeplastictrees.blog.category.repository.CategoryRepository;
import ee.fakeplastictrees.blog.category.service.CategoryService;
import ee.fakeplastictrees.blog.core.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static ee.fakeplastictrees.blog.core.Utils.mappers;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void getCategories() {
        Category category1 = CategoryTestSupport.generateCategory();
        Category category2 = CategoryTestSupport.generateCategory();

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        assertThat(categoryService.getCategories()).containsExactlyInAnyOrder(
                mappers().category().categoryToCategoryDto(category1),
                mappers().category().categoryToCategoryDto(category2)
        );
    }

    @Test
    public void createCategory() {
        CategoryDto inputCategoryDto = CategoryTestSupport.generateCategoryDto();
        inputCategoryDto.setId(null);
        Category inputCategory = mappers().category().categoryDtoToCategory(inputCategoryDto);
        Category outputCategory = mappers().category().categoryDtoToCategory(inputCategoryDto);
        outputCategory.setId(randomAlphanumeric(10));

        when(categoryRepository.save(inputCategory)).thenReturn(outputCategory);

        CategoryDto createdCategory = categoryService.createCategory(inputCategoryDto);
        verify(categoryRepository, times(1)).save(inputCategory);
        assertThat(createdCategory.getId()).isEqualTo(outputCategory.getId());
        assertThat(createdCategory.getName()).isEqualTo(outputCategory.getName());
    }

    @Test
    public void putCategory() {
        CategoryDto inputCategoryDto = CategoryTestSupport.generateCategoryDto();
        Category category = mappers().category().categoryDtoToCategory(inputCategoryDto);

        when(categoryRepository.existsById(category.getId())).thenReturn(true);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto createdCategory = categoryService.updateCategory(inputCategoryDto);
        verify(categoryRepository, times(1)).save(category);
        assertThat(createdCategory.getId()).isEqualTo(category.getId());
        assertThat(createdCategory.getName()).isEqualTo(category.getName());
    }

    @Test
    public void putCategoryThatDoesNotExist() {
        CategoryDto categoryDto = CategoryTestSupport.generateCategoryDto();

        when(categoryRepository.existsById(categoryDto.getId())).thenReturn(false);

        assertThatThrownBy(() -> categoryService.updateCategory(categoryDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category was not found by identifier '%s'", categoryDto.getId());
    }

    @Test
    public void deleteCategory() {
        String categoryId = randomAlphanumeric(10);
        Category category = CategoryTestSupport.generateCategory();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(categoryId);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    public void deleteCategoryThatDoesNotExist() {
        String categoryId = randomAlphanumeric(10);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.deleteCategory(categoryId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category was not found by identifier '%s'", categoryId);

        verify(categoryRepository, times(0)).delete(any());
    }
}
