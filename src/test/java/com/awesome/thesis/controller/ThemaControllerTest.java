package com.awesome.thesis.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test für ThemaController.
 */
@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(ThemaController.class)
public class ThemaControllerTest {

  @Autowired
  MockMvc mvc;

  @MockitoBean
  ThemaEditor themaEditor;

  @MockitoBean
  FachgebieteEditor fachEditor;

  @MockitoBean
  VoraussetzungenEditor vorEditor;

  @MockitoBean
  ProfilEditor profilEditor;

  @Test
  @WithMockOAuth2User()
  @DisplayName("themaListe is accessible")
  void test_1() throws Exception {
    Thema thema = mock(Thema.class);
    when(themaEditor.getThema(anyInt())).thenReturn(thema);
    mvc.perform(get("/themen"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockOAuth2User()
  @DisplayName("thema is accessible")
  void test_2() throws Exception {
    Thema thema = mock(Thema.class);
    when(themaEditor.getThema(anyInt())).thenReturn(thema);
    mvc.perform(get("/thema/2"))
        .andExpect(model().attribute("thema", thema))
        .andExpect(status().isOk());
  }
}
