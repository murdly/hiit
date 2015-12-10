package com.bucket.akarbowy.hiit.exception;

/**
 * Created by akarbowy on 10.12.2015.
 */
public class FormNotValidException extends Exception {

  public FormNotValidException() {
    super();
  }

  public FormNotValidException(final String message) {
    super(message);
  }

  public FormNotValidException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public FormNotValidException(final Throwable cause) {
    super(cause);
  }
}
