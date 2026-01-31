package com.awesome.thesis.logic.application.service.voraussetzungen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testet die VoraussetzungenEditor-Klasse.
 */
public class VoraussetzungenEditorTest {

  private VoraussetzungenEditor vorEditor;
  private ThemaEditor themaEditor;
  private VoraussetzungenRepoI repo;

  @BeforeEach
  void setUp() {
    themaEditor = mock(ThemaEditor.class);
    repo = mock(VoraussetzungenRepoI.class);
    vorEditor = new VoraussetzungenEditor(repo, themaEditor);
  }

  @Test
  @DisplayName("add adds a Voraussetzung to the repo if it isnt already included")
  void test_1() {
    //Arrange
    when(repo.contains(any())).thenReturn(false);
    Voraussetzung voraussetzung = new Voraussetzung("hallo");

    //Act
    vorEditor.add(voraussetzung);

    //Assert
    verify(repo).add(voraussetzung);
  }

  @Test
  @DisplayName("getAll retuns all Voraussetzungen in a List")
  void test_2() {
    Voraussetzung voraussetzung = new Voraussetzung("hallo");
    when(repo.getAll()).thenReturn(Set.of(voraussetzung));

    List<Voraussetzung> vorList = vorEditor.getAll();

    assertThat(vorList).contains(voraussetzung);
  }

  @Test
  @DisplayName("remove removes a Voraussetzung from all Thema objects and from the repo")
  void test_3() {
    Voraussetzung voraussetzung = new Voraussetzung("hallo");

    vorEditor.remove(voraussetzung);

    verify(themaEditor).removeVoraussetzungForAll("hallo");
    verify(repo).remove(voraussetzung);
  }

  @Test
  @DisplayName("getAllString returns a Set of String of the saved Voraussetzungen")
  void test_4() {
    Voraussetzung voraussetzung = new Voraussetzung("hallo");
    when(repo.getAll()).thenReturn(Set.of(voraussetzung));

    Set<String> strings = vorEditor.getAllString();

    assertThat(strings).contains("hallo");
  }
}
