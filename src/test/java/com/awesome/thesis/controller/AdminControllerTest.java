package com.awesome.thesis.controller;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Kontakt;
import com.awesome.thesis.logic.domain.model.profil.Kontaktart;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProfilEditor editor;

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("get auf /admin/profilEdit/{id} funktioniert")
    void get_profilEdit() throws Exception {
        Profil profil = mock(Profil.class);
        when(editor.get(any())).thenReturn(profil);
        mockMvc.perform(get("/admin/profilEdit/1"))
                .andExpect(model().attribute("profil", profil))
                .andExpect(view().name("admin/profilEdit"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User()
    @DisplayName("get auf /admin/profilEdit/{id} funktioniert nicht ohne Rechte")
    void get_profilEdit_withoutRights() throws Exception {
        Profil profil = mock(Profil.class);
        when(editor.get(any())).thenReturn(profil);
        mockMvc.perform(get("/admin/profilEdit/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("post Name ändern funktioniert")
    void post_Name() throws Exception {
        mockMvc.perform(post("/admin/profilEdit/1")
                        .param("name", "test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("post Name ändern funktioniert")
    void post_Name_BackEnd() throws Exception {
        mockMvc.perform(post("/admin/profilEdit/1")
                .param("name", "test")
                .with(csrf()));
        verify(editor).editName("1", "test");
    }

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("post Kontakt löschen")
    void post_deleteKontakt() throws Exception {
        mockMvc.perform(post("/admin/profilEdit/1/deleteKontakt")
                        .param("label", "test")
                        .param("wert", "test@icloud.com")
                        .param("kontaktart", Kontaktart.EMAIL.toString())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("post Kontakt löschen")
    void post_deleteKontakt_BackEnd() throws Exception {
        mockMvc.perform(post("/admin/profilEdit/1/deleteKontakt")
                .param("label", "test")
                .param("wert", "test@icloud.com")
                .param("kontaktart", Kontaktart.EMAIL.toString())
                .with(csrf()));
        verify(editor).removeKontakt("1", new Kontakt("test", "test@icloud.com", Kontaktart.EMAIL));
    }

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("post Kontakt hinzufügen funktioniert")
    void post_addKontakt() throws Exception {
        mockMvc.perform(post("/admin/profilEdit/1/addEmail")
                        .param("label", "test")
                        .param("wert", "test@icloud.com")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("post Kontakt hinzufügen funktioniert")
    void post_addKontakt_BackEnd() throws Exception {
        mockMvc.perform(post("/admin/profilEdit/1/addEmail")
                .param("label", "test")
                .param("wert", "test@icloud.com")
                .with(csrf()));
        verify(editor).addEmail("1", "test", "test@icloud.com");
    }

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("post Kontakt hinzufügen funktioniert nicht bei fehlender Email")
    void post_addKontakt_keinWert() throws Exception {
        Profil profil = mock(Profil.class);
        when(editor.get(any())).thenReturn(profil);
        mockMvc.perform(post("/admin/profilEdit/1/addEmail")
                        .param("label", "test")
                        .param("wert", "")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("post Kontakt hinzufügen funktioniert nicht bei fehlender Email")
    void post_addKontakt_BackEnd_keinWert() throws Exception {
        Profil profil = mock(Profil.class);
        when(editor.get(any())).thenReturn(profil);
        mockMvc.perform(post("/admin/profilEdit/1/addEmail")
                .param("label", "test")
                .param("wert", "")
                .with(csrf()));
        verify(editor, never()).addEmail(any(), any(), any());
    }

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("post Kontakt hinzufügen funktioniert nicht bei falscher Email")
    void post_addKontakt_keineEmail() throws Exception {
        Profil profil = mock(Profil.class);
        when(editor.get(any())).thenReturn(profil);
        mockMvc.perform(post("/admin/profilEdit/1/addEmail")
                        .param("label", "test")
                        .param("wert", "test")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(roles = {"ADMIN"})
    @DisplayName("post Kontakt hinzufügen funktioniert nicht bei falscher Email")
    void post_addKontakt_BackEnd_keineEmail() throws Exception {
        Profil profil = mock(Profil.class);
        when(editor.get(any())).thenReturn(profil);
        mockMvc.perform(post("/admin/profilEdit/1/addEmail")
                .param("label", "test")
                .param("wert", "test")
                .with(csrf()));
        verify(editor, never()).addEmail(any(), any(), any());
    }
}