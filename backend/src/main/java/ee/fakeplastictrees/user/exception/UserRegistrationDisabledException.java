package ee.fakeplastictrees.user.exception;

import ee.fakeplastictrees.core.exceiption.PublicException;
import ee.fakeplastictrees.core.model.MessageDefinition;
import org.springframework.http.HttpStatus;

public class UserRegistrationDisabledException extends PublicException {
  public UserRegistrationDisabledException() {
    super("user registration is disabled", MessageDefinition.USER_REGISTRATION_DISABLED, HttpStatus.CONFLICT);
  }
}
