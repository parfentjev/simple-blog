package ee.fakeplastictrees.blog.core.configuration;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import ee.fakeplastictrees.blog.core.exceptions.ResourceAlreadyExistsException;
import ee.fakeplastictrees.blog.core.exceptions.ResourceNotFoundException;
import ee.fakeplastictrees.blog.core.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static java.lang.String.format;

@RestControllerAdvice
public class ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(Throwable throwable) {
        throwable.printStackTrace();

        return errorResponse("This request can't be completed due to an unexpected error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return errorResponse("Missing mandatory request param(s).", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String validationErrors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> format("'%s' %s", ((FieldError) error).getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        return errorResponse("Missing mandatory request body param(s): " + validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> userAlreadyExistsException(ResourceAlreadyExistsException e) {
        return errorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException e) {
        return errorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> authenticationException(AuthenticationException e) {
        return errorResponse("Invalid credentials.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException e) {
        return errorResponse("Access denied.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> jwtVerificationException(JWTVerificationException e) {
        return errorResponse("Authentication error: " + e.getMessage() + ".", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> jwtDecodeException(JWTDecodeException e) {
        return errorResponse("Token is not valid.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return errorResponse("Failed to deserialize the request body.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return errorResponse("Method is not supported.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return errorResponse("Media type is not supported.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    private ResponseEntity<ErrorResponse> errorResponse(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(builders().errorResponse().message(message).build(), httpStatus);
    }
}
