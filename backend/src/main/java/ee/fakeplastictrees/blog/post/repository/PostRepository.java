package ee.fakeplastictrees.blog.post.repository;

import ee.fakeplastictrees.blog.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByVisibleTrue(Pageable pageable);
}
