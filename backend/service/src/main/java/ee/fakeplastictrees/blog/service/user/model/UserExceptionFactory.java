package ee.fakeplastictrees.blog.service.user.model;

import ee.fakeplastictrees.blog.service.core.exceiption.PublicException;
import ee.fakeplastictrees.blog.service.core.exceiption.PublicExceptionFactory;
import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class UserExceptionFactory {
  public PublicException usernameAlreadyTaken() {
    return PublicExceptionFactory.withData("username is already taken", MessageDefinition.USERNAME_ALREADY_TAKEN, HttpStatus.CONFLICT);
  }

  public PublicException notFound() {
    return PublicExceptionFactory.withData("user was not found", MessageDefinition.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
  }

  public PublicException registrationDisabled() {
    return PublicExceptionFactory.withData("user registration is disabled", MessageDefinition.USER_REGISTRATION_DISABLED, HttpStatus.CONFLICT);
  }
}
