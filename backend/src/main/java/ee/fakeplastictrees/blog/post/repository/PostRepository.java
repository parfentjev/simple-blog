package ee.fakeplastictrees.blog.post.repository;

import ee.fakeplastictrees.blog.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ListCrudRepository<Post, String> {
    Page<Post> findByVisibleTrue(Pageable pageable);

    Page<Post> findByVisibleTrueAndCategoriesId(Pageable pageable, String categoryId);
}
