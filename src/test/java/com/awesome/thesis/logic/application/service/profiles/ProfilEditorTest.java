package com.awesome.thesis.logic.application.service.profiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.profil.ProfilDateiValue;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontakt;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontaktart;
import com.awesome.thesis.logic.domain.model.profil.ProfilLink;
import com.awesome.thesis.logic.domain.model.profil.ProfilThemaValue;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class ProfilEditorTest {
  ProfileRepoI profile;
  FachgebieteEditor fachgebieteEditor;
  
  @BeforeEach
  void dependencies() {
    profile = mock(ProfileRepoI.class);
    fachgebieteEditor = mock(FachgebieteEditor.class);
  }
  
  @Test
  @DisplayName("getAll() returns all profiles of Repo")
  void test_getAll() {
    //Arrange
    List<Profil> p = List.of(new Profil(1, "test"));
    when(profile.getAll()).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act + Assert
    assertThat(editor.getAll()).isEqualTo(p);
  }
  
  @Test
  @DisplayName("an existing Profil can be loaded from the database")
  void test_get() {
    //Arrange
    when(profile.containsKey(anyInt())).thenReturn(true);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.get(1);
    
    //Assert
    verify(profile).get(1);
  }
  
  @Test
  @DisplayName("an existing Profil can be loaded from the database")
  void test_getProfil() {
    //Arrange
    when(profile.containsKey(anyInt())).thenReturn(true);
    Profil p = new Profil(1, "test");
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act + Assert
    assertThat(editor.get(1)).isEqualTo(p);
  }
  
  @Test
  @DisplayName("a non existing Profile can't be loaded from the Database")
  void test_getNotFound() {
    //Arrange
    when(profile.containsKey(anyInt())).thenReturn(false);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act + Assert
    assertThrows(IllegalArgumentException.class, () -> editor.get(1));
  }
  
  @Test
  @DisplayName("getFitting with empty set returns all profiles")
  void test_getFittingEmpty() {
    //Arrange
    List<Profil> p = List.of(new Profil(1, "test"));
    when(profile.getAll()).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    assertThat(editor.getFitting(new HashSet<>())).isEqualTo(p);
  }
  
  @Test
  @DisplayName("getFitting only returns profiles that fit the interests")
  void test_getFitting() {
    //Arrange
    Profil p1 = mock(Profil.class);
    when(p1.fitsInterests(any())).thenReturn(true);
    Profil p2 = mock(Profil.class);
    when(p2.fitsInterests(any())).thenReturn(false);
    List<Profil> p = List.of(p1, p2);
    when(profile.getAll()).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    List<Profil> r = editor.getFitting(Set.of("test"));
    
    // Assert
    assertThat(r).contains(p1);
    assertThat(r).doesNotContain(p2);
  }
  
  @Test
  @DisplayName("getMatching with empty set returns all profiles")
  void test_getMatchingEmpty() {
    //Arrange
    List<Profil> p = List.of(new Profil(1, "test"));
    when(profile.getAll()).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    assertThat(editor.getMatching(new HashSet<>())).isEqualTo(p);
  }
  
  @Test
  @DisplayName("getMatching returns profiles sorted descending")
  void test_getMatching() {
    //Arrange
    Profil p1 = mock(Profil.class);
    when(p1.compRank(any())).thenReturn(2L);
    Profil p2 = mock(Profil.class);
    when(p2.compRank(any())).thenReturn(1L);
    List<Profil> p = List.of(p1, p2);
    when(profile.getAll()).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    List<Profil> r = editor.getMatching(Set.of("test"));
    
    // Assert
    assertThat(r).containsExactlyInAnyOrder(p1, p2);
  }
  
  @Test
  @DisplayName("An existing profile can't be created again")
  void test_createExisting() {
    //Arrange
    when(profile.containsKey(anyInt())).thenReturn(true);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.create(1, "test");
    
    //Assert
    verify(profile, never()).save(any());
  }
  
  @Test
  @DisplayName("An non-existing profile can be created again")
  void test_create() {
    //Arrange
    when(profile.containsKey(anyInt())).thenReturn(false);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.create(1, "test");
    
    //Assert
    verify(profile).save(new Profil(1, "test"));
  }
  
  @Test
  @DisplayName("edit Name saves Profil with new Name")
  void test_editName() {
    //Arrange
    Profil p = new Profil(1, "test");
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.editName(1, "neuerName");
    
    //Assert
    assertThat(editor.get(1).getName()).isEqualTo("neuerName");
  }
  
  @Test
  @DisplayName("addEmail adds Email and saves Profil")
  void test_addEmail() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.addEmail(1, "test", "mail");
    
    //Assert
    InOrder inOrder = inOrder(p, profile);
    inOrder.verify(p).addEmail("test", "mail");
    inOrder.verify(profile).save(p);
  }
  
  @Test
  @DisplayName("addTel adds Phone Number and saves Profil")
  void test_addTel() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.addTel(1, "test", "tel");
    
    //Assert
    InOrder inOrder = inOrder(p, profile);
    inOrder.verify(p).addTel("test", "tel");
    inOrder.verify(profile).save(p);
  }
  
  @Test
  @DisplayName("remove Kontakt removes Kontakt and saves Profil")
  void test_removeKontakt() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    ProfilKontakt kontakt = new ProfilKontakt("test", "mail", ProfilKontaktart.EMAIL);
    
    //Act
    editor.removeKontakt(1, kontakt);
    
    //Assert
    InOrder inOrder = inOrder(p, profile);
    inOrder.verify(p).removeKontakt(kontakt);
    inOrder.verify(profile).save(p);
  }
  
  @Test
  @DisplayName("adding Fachgebiet also adds them as Fachgebiet Aggregate")
  void test_addFachgebiet() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.addFachgebiet(1, "test");
    
    //Assert
    InOrder inOrder = inOrder(fachgebieteEditor, p, profile);
    inOrder.verify(fachgebieteEditor).add("test");
    inOrder.verify(p).addFachgebiet("test");
    inOrder.verify(profile).save(p);
  }
  
  @Test
  @DisplayName("remove Fachgebiet also removes them as Fachgebiet Aggregate after updating repo")
  void test_removeFachgebiet() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.removeFachgebiet(1, "test");
    
    //Assert
    InOrder inOrder = inOrder(p, profile, fachgebieteEditor);
    inOrder.verify(p).removeFachgebiet("test");
    inOrder.verify(profile).save(p);
    inOrder.verify(fachgebieteEditor).remove("test");
  }
  
  @Test
  @DisplayName("addLink adds Link and saves Profil")
  void test_addLink() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.addLink(1, "url", "test");
    
    //Assert
    InOrder inOrder = inOrder(p, profile);
    inOrder.verify(p).addLink(new ProfilLink("url", "test"));
    inOrder.verify(profile).save(p);
  }
  
  @Test
  @DisplayName("removeLink removes Link and saves Profil")
  void test_removeLink() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    ProfilLink link = new ProfilLink("url", "test");
    
    //Act
    editor.removeLink(1, link);
    
    //Assert
    InOrder inOrder = inOrder(p, profile);
    inOrder.verify(p).removeLink(link);
    inOrder.verify(profile).save(p);
  }
  
  @Test
  @DisplayName("addThema adds Thema and saves Profil")
  void test_addThema() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.addThema(1, 1, "test");
    
    //Assert
    InOrder inOrder = inOrder(p, profile);
    inOrder.verify(p).addThema(new ProfilThemaValue(1, "test"));
    inOrder.verify(profile).save(p);
  }
  
  @Test
  @DisplayName("removeThema removes Thema and saves Profil")
  void test_removeThema() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    ProfilThemaValue thema = new ProfilThemaValue(1, "test");
    
    //Act
    editor.removeThema(1, 1);
    
    //Assert
    InOrder inOrder = inOrder(p, profile);
    inOrder.verify(p).removeThema(thema);
    inOrder.verify(profile).save(p);
  }
  
  @Test
  @DisplayName("addDatei adds Datei and saves Profil")
  void test_addDatei() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.addDatei(1, "id", "test", "test");
    
    //Assert
    InOrder inOrder = inOrder(p, profile);
    inOrder.verify(p).addDatei(new ProfilDateiValue("id", "test", "test"));
    inOrder.verify(profile).save(p);
  }
  
  @Test
  @DisplayName("removeLink removes Link and saves Profil")
  void test_removeDatei() {
    //Arrange
    Profil p = mock(Profil.class);
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    ProfilDateiValue datei = new ProfilDateiValue("id", "test", "test");
    
    //Act
    editor.removeDatei(1, "id");
    
    //Assert
    InOrder inOrder = inOrder(p, profile);
    inOrder.verify(p).removeDatei(datei);
    inOrder.verify(profile).save(p);
  }
}