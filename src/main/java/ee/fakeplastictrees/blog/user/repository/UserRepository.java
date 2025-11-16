package ee.fakeplastictrees.blog.user.repository;

import ee.fakeplastictrees.blog.user.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
  Optional<User> findByUsername(String username);
}
