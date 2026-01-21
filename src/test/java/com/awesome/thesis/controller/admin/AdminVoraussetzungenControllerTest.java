package com.awesome.thesis.controller.admin;

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
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test für AdminVoraussetzungController.
 */
@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(AdminVoraussetzungenController.class)
public class AdminVoraussetzungenControllerTest {

  @Autowired
  MockMvc mvc;

  @MockitoBean
  ProfilEditor editor;

  @MockitoBean
  VoraussetzungenEditor vorEditor;

  @MockitoBean
  ThemaEditor themaEditor;

  @Test
  @WithMockOAuth2User(roles = {"ADMIN"}, id = 1)
  @DisplayName("Ein Admin kann die voraussetzungen Seite aufrufen")
  void test_1() throws Exception {
    mvc.perform(get("/admin/module/edit"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockOAuth2User(roles = {"ADMIN"}, id = 1)
  @DisplayName("Ein Admin kann Voraussetzungen hinzufügen")
  void test_2() throws Exception {
    mvc.perform(post("/admin/addVoraussetzung").param("voraussetzung", "a").with(csrf()));
    verify(vorEditor).add(new Voraussetzung("a"));
  }

  @Test
  @WithMockOAuth2User(roles = {"ADMIN"}, id = 1)
  @DisplayName("Ein Admin kann Voraussetzungen löschen")
  void test_3() throws Exception {
    mvc.perform(post("/admin/removeVoraussetzung").param("voraussetzung", "a").with(csrf()));
    verify(vorEditor).remove(new Voraussetzung("a"));
  }

}
