package ee.fakeplastictrees.blog.core.configuration;

import ee.fakeplastictrees.blog.core.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static ee.fakeplastictrees.blog.core.Utils.builders;

@RestControllerAdvice
public class ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(Throwable throwable) {
        throwable.printStackTrace();
        return new ResponseEntity<>(builders().errorResponse().message("This request can't be completed due to an unexpected error.").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new ResponseEntity<>(builders().errorResponse().message("Missing mandatory request param(s).").build(), HttpStatus.BAD_REQUEST);
    }
}
