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
import java.util.ArrayList;
import java.util.List;
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
  @DisplayName("get auf /betreuende/{id}")
  void test_getProfilId() throws Exception {
    Profil profil = mock(Profil.class);
    when(profilEditor.get(anyInt())).thenReturn(profil);
    mockMvc.perform(get("/betreuende/1"))
        .andExpect(model().attribute("profil", profil))
        .andExpect(view().name("profiles/profil"))
        .andExpect(status().isOk());
  }
  
  @Test
  @WithMockOAuth2User()
  @DisplayName("get auf /profile")
  void test_getProfile() throws Exception {
    List<Profil> profile = new ArrayList<>();
    when(profilEditor.getAll()).thenReturn(profile);
    mockMvc.perform(get("/betreuende"))
        .andExpect(model().attribute("profile", profile))
        .andExpect(status().isOk())
        .andExpect(view().name("profiles/profile"));
  }
}