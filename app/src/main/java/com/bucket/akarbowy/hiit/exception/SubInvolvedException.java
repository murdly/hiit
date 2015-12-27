package com.bucket.akarbowy.hiit.exception;

/**
 * Created by akarbowy on 10.12.2015.
 */
public class SubInvolvedException extends Exception {

  public SubInvolvedException() {
    super();
  }

  public SubInvolvedException(final String message) {
    super(message);
  }

  public SubInvolvedException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public SubInvolvedException(final Throwable cause) {
    super(cause);
  }
}
