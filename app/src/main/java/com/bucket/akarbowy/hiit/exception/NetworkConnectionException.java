package com.bucket.akarbowy.hiit.exception;

/**
 * Created by akarbowy on 10.12.2015.
 */
public class NetworkConnectionException extends Exception {

  public NetworkConnectionException() {
    super();
  }

  public NetworkConnectionException(final String message) {
    super(message);
  }

  public NetworkConnectionException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public NetworkConnectionException(final Throwable cause) {
    super(cause);
  }
}
