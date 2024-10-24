package ee.fakeplastictrees.blog.service.user.exception;

import ee.fakeplastictrees.blog.service.core.exceiption.PublicException;
import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import org.springframework.http.HttpStatus;

public class UserRegistrationDisabledException extends PublicException {
  public UserRegistrationDisabledException() {
    super("user registration is disabled", MessageDefinition.USER_REGISTRATION_DISABLED, HttpStatus.CONFLICT);
  }
}
