package com.awesome.thesis.persistence.voraussetzungen;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import com.awesome.thesis.persistence.themen.ThemenDbRepository;
import com.awesome.thesis.persistence.voraussetzungen.dtos.VoraussetzungDto;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * Tests for the VoraussetzungRepoImpl.
 */
public class VoraussetzungenRepoImplTest {

  private VoraussetzungenRepoImpl repo;
  private VoraussetzungenDbRepository db;

  @BeforeEach
  public void setUp() {
    db = mock(VoraussetzungenDbRepository.class);
    repo = new VoraussetzungenRepoImpl(db);
  }

  @Test
  @DisplayName("add saves the Voraussetzung to the database")
  void test_1() {
    Voraussetzung voraussetzung = new Voraussetzung("propra");
    repo.add(voraussetzung);
    verify(db).save(new VoraussetzungDto("propra", null));
  }

  @Test
  @DisplayName("remove removes the Voraussetzung from the database")
  void test_2() {
    Voraussetzung voraussetzung = new Voraussetzung("propra");
    repo.remove(voraussetzung);
    verify(db).deleteById("propra");
  }

  @Test
  @DisplayName("getAll returns the expected result")
  void test_3() {
    when(db.findAll()).thenReturn(
        Set.of(new VoraussetzungDto("propra", 1))
    );
    Set<Voraussetzung> vor = repo.getAll();
    assertThat(vor).contains(new Voraussetzung("propra", 1));
  }

  @Test
  @DisplayName("contains returns the correct boolean")
  void test_4() {
    when(db.existsById("propra")).thenReturn(true);
    boolean result = repo.contains(new Voraussetzung("propra"));
    assertThat(result).isTrue();
  }

}
