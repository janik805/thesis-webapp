package com.awesome.thesis.persistence.themen;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the ThemaRepoImpl Class.
 */
public class ThemaRepoImplTest {

  private ThemenDbRepository repo;

  @BeforeEach
  void setUp() {
    repo = mock(ThemenDbRepository.class);
  }

  @Test
  @DisplayName("save properly saves a Thema in the database")
  void test_1() {

  }

}
