package com.awesome.thesis.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.files.DateiService;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.files.DateiInfos;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

/**
 * Klasse zum Testen der DateiController-Klasse.
 */
@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(DateiController.class)
class DateiControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  ProfilEditor profilEditor;

  @MockitoBean
  ThemaEditor themaEditor;

  @MockitoBean
  DateiService dateiService;

  private OAuth2AuthenticationToken authToken(Integer profilId) {
    Map<String, Object> attributes = Map.of("id", profilId);

    OAuth2User user = new DefaultOAuth2User(
        List.of(new SimpleGrantedAuthority("ROLE_USER")),
        attributes,
        "id"
    );

    return new OAuth2AuthenticationToken(
        user,
        user.getAuthorities(),
        "keycloak"
    );
  }

  @Test
  @WithMockOAuth2User(roles = {"BETREUENDE"})
  void annehmenErfolgreich() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "datei",
        "test.pdf",
        "text/plain",
        "Hallo".getBytes(StandardCharsets.UTF_8)
    );

    mockMvc.perform(multipart("/betreuende/datei/create")
            .file(file)
            .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/betreuende/profilEdit"));
  }

  @Test
  @WithMockOAuth2User(roles = {"BETREUENDE"})
  void themaAnnehmenErfolgreich() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "datei",
        "test.pdf",
        "text/plain",
        "Hallo".getBytes(StandardCharsets.UTF_8)
    );

    Thema thema = mock(Thema.class);

    when(themaEditor.getThema(any())).thenReturn(thema);
    when(themaEditor.allowedEdit(anyLong(), eq(thema))).thenReturn(true);
    when(dateiService.dateiSpeichern(any(MultipartFile.class), any()))
        .thenReturn(new DateiInfos("test.pdf", "beschreibung"));

    mockMvc.perform(multipart("/thema/datei/1/create")
            .file(file)
            .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/themaEdit/1"));
  }

  @Test
  @WithMockOAuth2User(roles = {"BETREUENDE"})
  void deleteProfilDateiRedirectedZuProfilEdit() throws Exception {
    mockMvc.perform(post("/betreuende/datei/delete/test")
            .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/betreuende/profilEdit"));
  }

  @Test
  @WithMockOAuth2User
  void downloadDateiFunktioniert() throws Exception {
    Resource resource = mock(Resource.class);

    when(dateiService.dateiLaden("test.pdf"))
        .thenReturn(resource);

    mockMvc.perform(get("/datei/view/test")
                    .param("name", "test.pdf"))
        .andExpect(status().isOk());
  }

}