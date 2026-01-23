package com.awesome.thesis.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(ProfilController.class)
class ProfilControllerTest {
  @Autowired
  MockMvc mockMvc;
  
  @MockitoBean
  ProfilEditor profilEditor;
  
  @MockitoBean
  FachgebieteEditor fachgebieteEditor;
  
  @Test
  @WithMockOAuth2User()
  @DisplayName("get auf /profile ohne Parameter")
  void test_getProfile() throws Exception {
    //Arrange
    List<Profil> p = List.of(new Profil(1, "test"));
    when(profilEditor.getFitting(null)).thenReturn(p);
    Set<String> f = Set.of("fachgebiet");
    when(fachgebieteEditor.getAll()).thenReturn(f);
    
    //Act + Assert
    mockMvc.perform(get("/betreuende"))
        .andExpect(model().attribute("fachgebiete", f))
        .andExpect(model().attribute("profile", p))
        .andExpect(status().isOk())
        .andExpect(view().name("profiles/profile"));
  }
  
  @Test
  @WithMockOAuth2User()
  @DisplayName("get auf /profile mit Parameter")
  void test_getProfileParam() throws Exception {
    //Arrange
    List<Profil> p = List.of(new Profil(1, "test"));
    when(profilEditor.getFitting(Set.of("test"))).thenReturn(p);
    Set<String> f = Set.of("fachgebiet");
    when(fachgebieteEditor.getAll()).thenReturn(f);
    
    //Act + Assert
    mockMvc.perform(get("/betreuende")
            .param("interessen", "test"))
        .andExpect(model().attribute("fachgebiete", f))
        .andExpect(model().attribute("profile", p))
        .andExpect(status().isOk())
        .andExpect(view().name("profiles/profile"));
  }
  
  @Test
  @WithMockOAuth2User()
  @DisplayName("get auf /betreuende/{id}")
  void test_getProfilId() throws Exception {
    //Arrange
    Profil profil = new Profil(1, "test");
    when(profilEditor.get(anyInt())).thenReturn(profil);
    
    //Act + Assert
    mockMvc.perform(get("/betreuende/1"))
        .andExpect(model().attribute("profil", profil))
        .andExpect(view().name("profiles/profil"))
        .andExpect(status().isOk());
  }
  
  @Test
  @WithMockOAuth2User
  @DisplayName("Exception Handler test funktioniert")
  void test_exception() throws Exception {
    //Arrange
    when(profilEditor.get(anyInt())).thenThrow(new IllegalArgumentException("test"));
    
    //Act
    mockMvc.perform(get("/betreuende/1"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("errorMessage", "test"))
        .andExpect(view().name("profiles/profilError"));
  }
  
}