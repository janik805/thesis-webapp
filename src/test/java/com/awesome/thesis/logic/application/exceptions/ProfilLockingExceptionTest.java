package com.awesome.thesis.logic.application.exceptions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProfilLockingExceptionTest {
  @Test
  @DisplayName("getMessage git Error Message zurück")
  void test_getMessage() {
    //Arrange
    ProfilLockingException e = new ProfilLockingException("test");
    
    //Act + Asser
    assertThat(e.getMessage()).isEqualTo("test");
  }
}