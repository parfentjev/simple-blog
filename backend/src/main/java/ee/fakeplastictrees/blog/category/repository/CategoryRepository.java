package ee.fakeplastictrees.blog.category.repository;

import ee.fakeplastictrees.blog.category.model.Category;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface CategoryRepository extends ListCrudRepository<Category, String> {
    Optional<Category> findByNameIgnoreCase(String name);
}
