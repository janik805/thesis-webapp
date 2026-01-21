package com.awesome.thesis.controller.betreuende;


import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test für BetreuendeThemaCreateController.
 */
@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(BetreuendeThemaCreateController.class)
public class BetreuendeThemaCreateControllerTest {

  @Autowired
  MockMvc mvc;

  @MockitoBean
  ThemaEditor themaEditor;

  @MockitoBean
  ProfilEditor profilEditor;

  @Test
  @WithMockOAuth2User(roles = {"BETREUENDE"})
  @DisplayName("Die themaCreate Seite ist erreichbar")
  void test_1() throws Exception {
    mvc.perform(get("/thema/create"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
  @DisplayName("Nach dem Erstellen des Themas wird man redirected")
  void test_2() throws Exception {
    mvc.perform(post("/thema/create").param("titel", "egal").param("beschreibung", "egal")
            .with(csrf()))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
  @DisplayName("Das thema mit passender id wird erstellt")
  void test_3() throws Exception {
    mvc.perform(post("/thema/create").param("titel", "egal").param("beschreibung", "egal")
            .with(csrf()))
        .andExpect(status().is3xxRedirection());
    Thema thema = new Thema("titel", 1);
    verify(themaEditor).addThema(thema, 1);
  }
}
