package com.awesome.thesis.persistence.profiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.awesome.thesis.logic.application.exceptions.ProfilLockingException;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.profil.ProfilDateiValue;
import com.awesome.thesis.logic.domain.model.profil.ProfilFachgebiet;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontakt;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontaktart;
import com.awesome.thesis.logic.domain.model.profil.ProfilLink;
import com.awesome.thesis.logic.domain.model.profil.ProfilThemaValue;
import com.awesome.thesis.persistence.profiles.dtos.ProfilDateiValueDto;
import com.awesome.thesis.persistence.profiles.dtos.ProfilDto;
import com.awesome.thesis.persistence.profiles.dtos.ProfilFachgebietDto;
import com.awesome.thesis.persistence.profiles.dtos.ProfilKontaktDto;
import com.awesome.thesis.persistence.profiles.dtos.ProfilLinkDto;
import com.awesome.thesis.persistence.profiles.dtos.ProfilThemaValueDto;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.OptimisticLockingFailureException;

class ProfileRepoImplTest {
  private ProfileDbRepository dbRepository;
  
  @BeforeEach
  void dependencies() {
    dbRepository = mock(ProfileDbRepository.class);
  }
  
  @Test
  @DisplayName("get findet Profile über die id")
  void get() {
    //Arrange
    when(dbRepository.findById(anyInt())).thenReturn(new ProfilDto(1, 0, "test",
        new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()));
    ProfileRepoImpl repo = new ProfileRepoImpl(dbRepository);
    
    //Act
    Profil p = repo.get(1);
    
    //Assert
    assertThat(p.getId()).isEqualTo(1);
    assertThat(p.getVersion()).isEqualTo(0);
    assertThat(p.getName()).isEqualTo("test");
  }
  
  @Test
  @DisplayName("get mapped richtig")
  void get_Mapping() {
    //Arrange
    when(dbRepository.findById(anyInt())).thenReturn(new ProfilDto(1, 0, "test",
        Set.of(new ProfilKontaktDto("label", "wert", "TEL")),
        Set.of(new ProfilFachgebietDto("fachgebiet")),
        Set.of(new ProfilLinkDto("url", "text")),
        Set.of(new ProfilThemaValueDto(1, "name")),
        Set.of(new ProfilDateiValueDto("id", "name", "beschreibung"))));
    ProfileRepoImpl repo = new ProfileRepoImpl(dbRepository);
    
    //Act
    Profil p = repo.get(1);
    
    //Assert
    assertThat(p.getId()).isEqualTo(1);
    assertThat(p.getVersion()).isEqualTo(0);
    assertThat(p.getName()).isEqualTo("test");
    assertThat(p.getKontakte())
        .containsExactly(new ProfilKontakt("label", "wert", ProfilKontaktart.TEL));
    assertThat(p.getFachgebiete()).containsExactly("fachgebiet");
    assertThat(p.getLinks()).containsExactly(new ProfilLink("url", "text"));
    assertThat(p.getThemen()).containsExactly(new ProfilThemaValue(1, "name"));
    assertThat(p.getDateien())
        .containsExactly(new ProfilDateiValue("id", "name", "beschreibung"));
  }
  
  @Test
  @DisplayName("Profile werden mit Id gefunden")
  void test_containsKey() {
    //Arrange
    ProfileRepoImpl repo = new ProfileRepoImpl(dbRepository);
    
    //Act
    repo.containsKey(1);
    
    //Assert
    verify(dbRepository).existsById(1);
  }
  
  @Test
  @DisplayName("Profile werden gespeichert")
  void test_save() {
    //Arrange
    Profil profil = new Profil(1, "test");
    ProfileRepoImpl repo = new ProfileRepoImpl(dbRepository);
    
    //Act
    repo.save(profil);
    
    //Assert
    verify(dbRepository).save(new ProfilDto(1, null, "test",
        new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()));
  }
  
  @Test
  @DisplayName("save mapped richtig")
  void save_Mapping() {
    //Arrange
    Profil profil = new Profil(1, 0, "test",
        Set.of(new ProfilKontakt("label", "wert", ProfilKontaktart.TEL)),
        Set.of(new ProfilFachgebiet("fachgebiet")),
        Set.of(new ProfilLink("url", "text")),
        Set.of(new ProfilThemaValue(1, "name")),
        Set.of(new ProfilDateiValue("id", "name", "beschreibung")));
    ProfileRepoImpl repo = new ProfileRepoImpl(dbRepository);
    
    //Act
    repo.save(profil);
    
    //Assert
    verify(dbRepository).save(new ProfilDto(1, 0, "test",
        Set.of(new ProfilKontaktDto("label", "wert", "TEL")),
        Set.of(new ProfilFachgebietDto("fachgebiet")),
        Set.of(new ProfilLinkDto("url", "text")),
        Set.of(new ProfilThemaValueDto(1, "name")),
        Set.of(new ProfilDateiValueDto("id", "name", "beschreibung"))));
  }
  
  @Test
  @DisplayName("save kann mit Locking Exception umgehen")
  void test_save_Locking() {
    //Arrange
    when(dbRepository.save(any())).thenThrow(new OptimisticLockingFailureException("exception"));
    Profil profil = new Profil(1, "test");
    ProfileRepoImpl repo = new ProfileRepoImpl(dbRepository);
    
    //Act + Assert
    assertThrows(ProfilLockingException.class, () -> repo.save(profil));
  }
  
  @Test
  @DisplayName("delete funktioniert")
  void delete() {
    //Arrange
    ProfileRepoImpl repo = new ProfileRepoImpl(dbRepository);
    
    //Act
    repo.delete(1);
    
    //Assert
    verify(dbRepository).deleteById(1);
  }
  
  @Test
  @DisplayName("getAll funktioniert und mapped richtig")
  void getAll() {
    //Arrange
    when(dbRepository.findAll()).thenReturn(List.of(new ProfilDto(1, 0, "test",
            new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>())));
    ProfileRepoImpl repo = new ProfileRepoImpl(dbRepository);
    
    //Act
    List<Profil> p = repo.getAll();
    
    //Assert
    assertThat(p).containsExactly(new Profil(1, "test"));
  }
}