package ee.fakeplastictrees.blog.file.exception;

public class DeleteFileException extends RuntimeException {
  private static final String MESSAGE = "Filed to delete a file.";

  public DeleteFileException() {
    super(MESSAGE);
  }

  public DeleteFileException(Throwable t) {
    super(MESSAGE, t);
  }
}
