package com.awesome.thesis.logic.application.exceptions;

/**
 * Exception um Fehler beim Locking von Profil zu fangen.
 */
public class ProfilLockingException extends RuntimeException {
  private final String message;
  
  public ProfilLockingException(String message) {
    this.message = message;
  }
  
  public String getMessage() {
    return message;
  }
}
