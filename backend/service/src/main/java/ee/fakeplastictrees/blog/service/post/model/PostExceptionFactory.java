package ee.fakeplastictrees.blog.service.post.model;

import ee.fakeplastictrees.blog.service.core.exceiption.PublicException;
import ee.fakeplastictrees.blog.service.core.exceiption.PublicExceptionFactory;
import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class PostExceptionFactory {
  public PublicException notFound() {
    return PublicExceptionFactory.withData("post was not found", MessageDefinition.POST_NOT_FOUND, HttpStatus.NOT_FOUND);
  }
}
