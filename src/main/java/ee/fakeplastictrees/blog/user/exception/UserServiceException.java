package ee.fakeplastictrees.blog.user.exception;

public class UserServiceException extends RuntimeException {
  public static final String REGISTRATION_DISABLED = "Registration is disabled.";
  public static final String USERNAME_ALREADY_TAKEN = "Username is already taken.";

  public UserServiceException(String message) {
    super(message);
  }

  public UserServiceException(String message, Throwable t) {
    super(message, t);
  }
}
