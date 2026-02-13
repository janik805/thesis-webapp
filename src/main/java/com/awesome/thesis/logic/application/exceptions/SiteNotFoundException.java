package com.awesome.thesis.logic.application.exceptions;

/**
 * Exception bei fehlender Seite.
 */
public class SiteNotFoundException extends RuntimeException {
  private final String message;
  
  public SiteNotFoundException(String message) {
    this.message = message;
  }
  
  public String getMessage() {
    return message;
  }
}
