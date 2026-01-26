package com.awesome.thesis.logic.domain.model.profil;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProfilTest {
  Profil profil;
  
  @BeforeEach
  void set_up() {
    profil = new Profil(1, "test");
  }
  
  @Test
  @DisplayName("fachgebieteString returns all fachgebiete as a String")
  void test_fachgebieteString() {
    //Arrange
    profil.addFachgebiet("fachgebiet1");
    profil.addFachgebiet("fachgebiet2");
    
    //Act
    String r = profil.fachgebieteString();
    
    //Assert
    assertThat(r).contains("fachgebiet1");
    assertThat(r).contains("fachgebiet2");
  }
  
  @Test
  @DisplayName("fachgebieteString returns empty String if fachgebiete is empty")
  void test_fachgebieteStringEmpty() {
    //Act
    String r = profil.fachgebieteString();
    
    //Assert
    assertThat(r).isEmpty();
  }
  
  @Test
  @DisplayName("after addFachgebiete profil has Fachgebiet")
  void test_addFachgebiet() {
    //Act
    profil.addFachgebiet("fachgebiet");
    
    //Assert
    assertThat(profil.getFachgebiete().contains("fachgebiet")).isTrue();
  }
  
  @Test
  @DisplayName("hasFachgebiet is true if profil has fachgebiet")
  void test_hasFachgebietTrue() {
    //Arrange
    profil.addFachgebiet("fachgebiet");
    
    //Act + Assert
    assertThat(profil.hasFachgebiet("fachgebiet")).isTrue();
  }
  
  @Test
  @DisplayName("hasFachgebiet is true if profil has fachgebiet")
  void test_hasFachgebietFalse() {
    //Act + Assert
    assertThat(profil.hasFachgebiet("fachgebiet")).isFalse();
  }
  
  @Test
  @DisplayName("remove Fachgebiete removes fachgebiet")
  void test_removeFachgebiete() {
    //Arrange
    profil.addFachgebiet("fachgebiet");
    
    //Act
    profil.removeFachgebiet("fachgebiet");
    
    //Assert
    assertThat(profil.hasFachgebiet("fachgebiet")).isFalse();
  }
  
  @Test
  @DisplayName("fitsInterests returns true when interessen ist empty")
  void test_fitsInterestsEmpty() {
    //Act + Assert
    assertThat(profil.fitsInterests(Set.of())).isTrue();
  }
  
  @Test
  @DisplayName("fitsInterests returns true when fachgebiet contains interessen")
  void test_fitsInterestsTrue() {
    //Arrange
    profil.addFachgebiet("fachgebiet1");
    profil.addFachgebiet("fachgebiet2");
    
    //Act + Assert
    assertThat(profil.fitsInterests(Set.of("fachgebiet1"))).isTrue();
  }
  
  @Test
  @DisplayName("fitsInterests returns true when fachgebiet contains interessen")
  void test_fitsInterestsFalse() {
    //Arrange
    profil.addFachgebiet("fachgebiet1");
    
    //Act + Assert
    assertThat(profil.fitsInterests(Set.of("fachgebiet1", "fachgebiet2"))).isFalse();
  }
  
  @Test
  @DisplayName("compRank returns the number of fitting interests")
  void compRank() {
    //Arrange
    profil.addFachgebiet("fachgebiet1");
    profil.addFachgebiet("fachgebiet2");
    
    //Act + Assert
    assertThat(profil.compRank(Set.of("fachgebiet1"))).isEqualTo(1);
  }
  
  @Test
  @DisplayName("addThema removes old Thema and adds new Thema")
  void addThema() {
    //Arrange
    profil.addThema(new ProfilThemaValue(1, "test1"));
    
    //Act
    profil.addThema(new ProfilThemaValue(1, "test2"));
    
    //Assert
    assertThat(profil.getThemen().stream()
        .filter(t -> t.id() == 1)
        .anyMatch(t -> t.name().equals("test1"))).isFalse();
    assertThat(profil.getThemen().stream()
        .filter(t -> t.id() == 1)
        .anyMatch(t -> t.name().equals("test2"))).isTrue();
  }
  
  @Test
  @DisplayName("addDatei removes old Datei and adds new Datei")
  void addDatei() {
    //Arrange
    profil.addDatei(new ProfilDateiValue("1", "test1", null));
    
    //Act
    profil.addDatei(new ProfilDateiValue("1", "test2", null));
    
    //Assert
    assertThat(profil.getDateien().stream()
        .filter(t -> t.id().equals("1"))
        .anyMatch(t -> t.name().equals("test1"))).isFalse();
    assertThat(profil.getDateien().stream()
        .filter(t -> t.id().equals("1"))
        .anyMatch(t -> t.name().equals("test2"))).isTrue();
  }
  
  @Test
  @DisplayName("addTel works")
  void test_addTel() {
    //Act
    profil.addTel("testlabel", "testwert");
    
    //Assert
    assertThat(profil.getKontakte())
        .contains(new ProfilKontakt("testlabel", "testwert", ProfilKontaktart.TEL));
  }
  
  @Test
  @DisplayName("addEmail works")
  void test_addEmail() {
    //Act
    profil.addEmail("testlabel", "testwert");
    
    //Assert
    assertThat(profil.getKontakte())
        .contains(new ProfilKontakt("testlabel", "testwert", ProfilKontaktart.EMAIL));
  }
}