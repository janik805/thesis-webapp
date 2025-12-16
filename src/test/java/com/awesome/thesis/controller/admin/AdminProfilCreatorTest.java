package com.awesome.thesis.controller.admin;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(AdminProfilCreator.class)
class AdminProfilCreatorTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProfilEditor editor;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Ein Admin kann die ProfilCreate Seite aufrufen")
    void get_createProfil() throws Exception {
        mockMvc.perform(get("/admin/createProfile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    @DisplayName("Ohne Admin-Rechte kann die ProfilCreate Seite nicht aufgerufen werden")
    void get_createProfil_withoutRights() throws Exception {
        mockMvc.perform(get("/admin/createProfile"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("get gibt das richtige zurück")
    void get_createProfil_model() throws Exception {
        List<Profil> list = List.of();
        when(editor.getAll()).thenReturn(list);
        mockMvc.perform(get("/admin/createProfile"))
                .andExpect(model().attribute("profile", list))
        .andExpect(view().name("admin/profileAdmin"));
    }

    @Test
    @WithMockUser()
    @DisplayName("Ohne Admin-Rechte kann ein post nicht ausführen")
    void post_createProfil_withoutRights() throws Exception {
        mockMvc.perform(post("/admin/createProfile")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Ein Admin kann ein post auf profilCreate ausführen")
    void post_createProfil() throws Exception {
        mockMvc.perform(post("/admin/createProfile")
                        .param("id", "1")
                        .param("name", "test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Ein Admin kann ein post auf profilCreate ausführen")
    void post_createProfil_backEnd() throws Exception {
        mockMvc.perform(post("/admin/createProfile")
                        .param("id", "1")
                        .param("name", "test")
                        .with(csrf()));
        verify(editor).create("1", "test");
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("id darf nicht leer sein")
    void post_createProfil_backEnd_idCantBeEmpty() throws Exception {
        mockMvc.perform(post("/admin/createProfile")
                .param("id", "")
                .param("name", "test")
                .with(csrf()));
        verify(editor, never()).create(any(), any());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("id muss ein positiver int sein")
    void post_createProfil_backEnd_idNeedsToBeInt() throws Exception {
        mockMvc.perform(post("/admin/createProfile")
                .param("id", "sdf")
                .param("name", "test")
                .with(csrf()));
        verify(editor, never()).create(any(), any());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("id darf nicht leer sein")
    void post_createProfil_backEnd_NameCantBeEmpty() throws Exception {
        mockMvc.perform(post("/admin/createProfile")
                .param("id", "1")
                .param("name", "")
                .with(csrf()));
        verify(editor, never()).create(any(), any());
    }
}