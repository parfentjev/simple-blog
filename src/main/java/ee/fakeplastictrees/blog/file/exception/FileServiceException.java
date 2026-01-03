package ee.fakeplastictrees.blog.file.exception;

public class FileServiceException extends RuntimeException {
  public static final String EMPTY_FILE = "Empty file.";
  public static final String FAILED_TO_SAVE = "Failed to save a file.";
  public static final String FAILED_TO_DELETE = "Failed to delete a file.";

  public FileServiceException(String message) {
    super(message);
  }

  public FileServiceException(String message, Throwable t) {
    super(message, t);
  }
}
