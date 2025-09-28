package ee.fakeplastictrees.blog.file.repository;

import ee.fakeplastictrees.blog.file.model.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<File, String> {
  Page<File> findAll(Pageable pageable);
}
