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
  @DisplayName("adding Fachgebiet also adds them as Fachgebiet Aggregate")
  void test_addFachgebiet() {
    //Arrange
    Profil p = new Profil(1, "test");
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.addFachgebiet(1, "test");
    
    //Assert
    verify(fachgebieteEditor).add("test");
  }
  
  @Test
  @DisplayName("remove Fachgebiet also removes them as Fachgebiet Aggregate after updating repo")
  void test_removeFachgebiet() {
    //Arrange
    Profil p = new Profil(1, "test");
    when(profile.containsKey(anyInt())).thenReturn(true);
    when(profile.get(anyInt())).thenReturn(p);
    ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);
    
    //Act
    editor.removeFachgebiet(1, "test");
    
    //Assert
    InOrder inOrder = inOrder(profile, fachgebieteEditor);
    inOrder.verify(profile).save(p);
    inOrder.verify(fachgebieteEditor).remove("test");
  }
}