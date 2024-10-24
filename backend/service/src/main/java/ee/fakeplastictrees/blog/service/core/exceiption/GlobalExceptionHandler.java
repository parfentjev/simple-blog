package ee.fakeplastictrees.blog.service.core.exceiption;

import com.auth0.jwt.exceptions.JWTVerificationException;
import ee.fakeplastictrees.blog.codegen.model.ErrorDto;
import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<ErrorDto> publicException(PublicException e) {
    return errorResponse(e.getMessage(), e.getDefinition(), e.getStatus());
  }

  @ExceptionHandler
  public ResponseEntity<ErrorDto> validationException(MethodArgumentNotValidException e) {
    var messages = new StringBuilder();
    e.getFieldErrors().forEach(error -> {
      if (!messages.isEmpty()) {
        messages.append("; ");
      }

      messages.append(error.getField()).append(": ").append(error.getDefaultMessage());
    });

    return errorResponse(messages.toString(), MessageDefinition.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorDto> authenticationException(AuthenticationException e) {
    return errorResponse("bad credentials", MessageDefinition.BAD_CREDENTIALS, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorDto> accessDeniedException(AccessDeniedException e) {
    return errorResponse("access denied", MessageDefinition.ACCESS_DENIED, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorDto> jwtVerificationException(JWTVerificationException e) {
    return errorResponse("auth token is not valid", MessageDefinition.INVALID_AUTH_TOKEN, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorDto> exception(Throwable e) {
    log.error(e);

    return errorResponse("something went wrong", MessageDefinition.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorDto> errorResponse(String message, MessageDefinition definition, HttpStatus status) {
    return ResponseEntity
      .status(status)
      .body(new ErrorDto().message(message).messageDefinition(definition.toString()));
  }
}
