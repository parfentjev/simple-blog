package ee.fakeplastictrees.blog.user.repository;

import ee.fakeplastictrees.blog.user.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, String> {
    Optional<User> findByUsername(String username);
}
