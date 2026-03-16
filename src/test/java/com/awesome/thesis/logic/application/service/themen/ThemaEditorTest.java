package com.awesome.thesis.logic.application.service.themen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaDateiValue;
import com.awesome.thesis.logic.domain.model.themen.ThemaFachgebiet;
import com.awesome.thesis.logic.domain.model.themen.ThemaLink;
import com.awesome.thesis.logic.domain.model.themen.ThemaVoraussetzung;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test für ThemaEditor.
 */
public class ThemaEditorTest {

  private static Thema neuesThema() {
    return new Thema("Test", 180645494);
  }

  private ThemaRepoI repo;
  private ProfilEditor profilEditor;
  private ThemaEditor editor;
  private FachgebieteEditor fachEditor;

  @BeforeEach
  void setUp() {
    repo = mock(ThemaRepoI.class);
    profilEditor = mock(ProfilEditor.class);
    fachEditor = mock(FachgebieteEditor.class);
    editor = new ThemaEditor(repo, profilEditor, fachEditor);
  }

  @Test
  @DisplayName("When a new Link gets added, its saved in the repository")
  void test_1() {
    Thema thema = neuesThema();

    when(repo.get(2)).thenReturn(thema);
    when(repo.containsKey(2)).thenReturn(true);
    ThemaLink link = new ThemaLink("url", "beschreibung");

    //Act
    editor.addLink(2, "url", "beschreibung");

    //Assert
    verify(repo).save(any(Thema.class));
    assertThat(thema.getLinks()).contains(link);
  }

  @Test
  @DisplayName("When you try to add a link to a Thema"
      + " with non existent id, an Exception gets thrown")
  void test_1_5() {
    //Act && Assert
    assertThrows(NoSuchElementException.class, () -> editor.addLink(2, "url", "beschreibung"));

  }

  @Test
  @DisplayName("When a link gets removed it is no longer in the repository")
  void test_2() {
    //Arrange
    Thema thema = neuesThema();
    when(repo.get(2)).thenReturn(thema);
    when(repo.containsKey(2)).thenReturn(true);
    ThemaLink link = new ThemaLink("url", "beschreibung");
    editor.addLink(2, "url", "beschreibung");
    //Act
    editor.removeLink(2, link);

    //Assert
    verify(repo, times(2)).save(any(Thema.class));
    assertThat(thema.getLinks()).doesNotContain(link);
  }

  @Test
  @DisplayName("When setTitel is called, the title is actually changed")
  void test_3() {
    //Arrange
    Thema thema = neuesThema();
    when(repo.get(2)).thenReturn(thema);
    when(repo.containsKey(2)).thenReturn(true);

    //Act
    editor.editTitel(thema.getProfilId(), 2, "Hallo");

    //Assert
    assertThat(thema.getTitel()).isEqualTo("Hallo");
  }

  @Test
  @DisplayName("addThema adds a Thema with correct parameters")
  void test_5() {
    //Arrange
    Thema thema = new Thema(null, 0,  "hallo", "", 0, Set.of(), Set.of(), Set.of(), Set.of());
    Thema saved = new Thema(1, 0, "hallo", "", 0, Set.of(), Set.of(), Set.of(), Set.of());
    when(repo.save(any(Thema.class))).thenReturn(saved);

    //Act
    editor.addThema(thema, 2);

    //Assert
    verify(repo).save(any(Thema.class));
    verify(profilEditor).addThema(eq(2), eq(1), eq("hallo"));
  }

  @Test
  @DisplayName("When getThema is called with an id that exists, it returns the correct thema")
  void test_7() {
    //Arrange
    when(repo.containsKey(anyInt())).thenReturn(true);

    //Act
    editor.getThema(2);

    //Assert
    verify(repo).get(2);
  }

  @Test
  @DisplayName("When getThema is called with an id that does not exist in the database,"
      + " a NoSuchElementException gets thrown")
  void test_8() {
    //Arrange
    when(repo.containsKey(anyInt())).thenReturn(false);

    //Act && Assert
    assertThrows(NoSuchElementException.class, () -> editor.getThema(2));
  }

  @Test
  @DisplayName("editBeschreibung edits the description")
  public void test_9() {
    //Arrange
    Thema thema = neuesThema();
    when(repo.containsKey(anyInt())).thenReturn(true);
    when(repo.get(anyInt())).thenReturn(thema);
    when(editor.getThema(anyInt())).thenReturn(thema);

    //Act
    editor.editBeschreibung(1, "Hallo");

    //Assert
    assertThat(thema.getBeschreibung()).isEqualTo("Hallo");
  }

  @Test
  @DisplayName("deleteThema removes a Thema")
  public void test_11() {
    //Arrange
    Thema thema = neuesThema();
    when(repo.containsKey(anyInt())).thenReturn(true);
    when(repo.get(anyInt())).thenReturn(thema);
    when(editor.getThema(anyInt())).thenReturn(thema);

    //Act
    editor.deleteThema(2, 180645494);

    //Assert
    verify(repo).delete(2);
  }

  @Test
  @DisplayName("removeAllVoraussetzung removes a Voraussetzung from all Themas")
  void test_12() {
    //Arrange
    Thema thema = neuesThema();
    thema.updateVoraussetzungen(Set.of(new ThemaVoraussetzung("Propra")));
    when(repo.getThemen()).thenReturn(List.of(thema));

    //Act
    editor.removeAllVoraussetzung(new Voraussetzung("Propra"));

    //Assert
    assertThat(thema.getVoraussetzungen()).doesNotContain(new ThemaVoraussetzung("Propra"));

  }

  @Test
  @DisplayName("updateVoraussetzungen updates Voraussetzungen for Thema")
  void test_13() {
    //Arrange
    Thema thema = neuesThema();
    Set<String> voraussetzungen = (Set.of("Propra"));
    when(repo.containsKey(anyInt())).thenReturn(true);
    when(repo.get(anyInt())).thenReturn(thema);

    //Act
    editor.updateVoraussetzungen(2, voraussetzungen);

    //Assert
    assertThat(thema.getVoraussetzungen()).contains(new ThemaVoraussetzung("Propra"));
  }

  @Test
  @DisplayName("removeFachgebiet removes a Fachgebiet from Thema")
  void test_14() {
    //Arrange
    Thema thema = neuesThema();
    thema.addFachgebiet(new ThemaFachgebiet("Fach"));
    when(repo.containsKey(anyInt())).thenReturn(true);
    when(repo.get(anyInt())).thenReturn(thema);

    //Act
    editor.removeFachgebiet(2, "Fach");

    //Assert
    assertThat(thema.getFachgebiete()).doesNotContain(new ThemaFachgebiet("a"));
  }

  @Test
  @DisplayName("addFachgebiet adds a Fachgebiet to a Thema")
  void test_14_5() {
    Thema thema = neuesThema();
    when(repo.containsKey(anyInt())).thenReturn(true);
    when(repo.get(anyInt())).thenReturn(thema);

    editor.addFachgebiet(1, "hallo");

    assertThat(thema.getFachgebiete()).contains(new ThemaFachgebiet("hallo"));
  }

  @Test
  @DisplayName("You can add files to Thema")
  void test_15() {
    //Arrange
    Thema thema = neuesThema();
    when(repo.containsKey(anyInt())).thenReturn(true);
    when(repo.get(anyInt())).thenReturn(thema);
    ThemaDateiValue datei = new ThemaDateiValue("1", "hallo", "hallo");

    //Act
    editor.addDatei(1, "1", "hallo", "hallo");
    assertThat(thema.getDateien()).contains(datei);
  }

  @Test
  @DisplayName("getFitting actually returns the fitting Thema")
  void test_16() {
    //Arrange
    Thema thema1 = mock(Thema.class);
    when(thema1.fitsRequirements(any(), any())).thenReturn(true);
    Thema thema2 = mock(Thema.class);
    when(thema2.fitsRequirements(any(), any())).thenReturn(false);
    List<Thema> thema = new ArrayList<>();
    thema.add(thema1);
    thema.add(thema2);
    when(editor.getAll()).thenReturn(thema);

    //Act
    List<Thema> fitlerd = editor.getFitting(Set.of(), Set.of());

    //Assert
    assertThat(fitlerd).containsOnly(thema1);
  }

  @Test
  @DisplayName("sortRang sorts the rang by fitting Interessen and Voraussetzungen")
  void test_17() {
    //Arrange
    Thema thema1 = mock(Thema.class);
    when(thema1.calcRang(any(), any())).thenReturn(1L);
    Thema thema2 = mock(Thema.class);
    when(thema2.calcRang(any(), any())).thenReturn(2L);
    List<Thema> thema = new ArrayList<>();
    thema.add(thema1);
    thema.add(thema2);
    when(editor.getAll()).thenReturn(thema);

    //Act
    List<Thema> sorted = editor.sortRang(Set.of(), Set.of());

    //Assert
    assertThat(sorted.getFirst()).isEqualTo(thema2);
  }

  @Test
  @DisplayName("getVoraussetzung returns all Voraussetzungen of the Thema")
  void test_18() {
    //Arrange
    Thema thema = neuesThema();
    thema.updateVoraussetzungen(Set.of(new ThemaVoraussetzung("vor")));
    when(repo.containsKey(anyInt())).thenReturn(true);
    when(repo.get(anyInt())).thenReturn(thema);

    //Act
    Set<Voraussetzung> vor = editor.getVoraussetzungen(1);

    //Assert
    assertThat(vor).contains(new Voraussetzung("vor"));
  }

  @Test
  @DisplayName("removeVoraussetzungForAll removes Voraussetzung from all thema objects")
  void test_19() {
    //Arrange
    Thema thema = neuesThema();
    thema.updateVoraussetzungen(Set.of(new ThemaVoraussetzung("vor")));
    when(repo.containsKey(anyInt())).thenReturn(true);
    when(repo.get(anyInt())).thenReturn(thema);
    when(editor.getAll()).thenReturn(List.of(thema));

    editor.removeVoraussetzungForAll("vor");

    assertThat(thema.getVoraussetzungen()).doesNotContain(new ThemaVoraussetzung("vor"));
  }



}
