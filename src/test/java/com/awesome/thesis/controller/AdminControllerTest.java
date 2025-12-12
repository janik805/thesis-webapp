package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProfilEditor editor;

    @Test
    @DisplayName("get auf /admin/profilEdit/{id}")
    void get_profilEdit() throws Exception {
        Profil profil = mock(Profil.class);
        when(editor.get(any())).thenReturn(profil);
        mockMvc.perform(get("/admin/profilEdit/1"))
                .andExpect(model().attribute("profil", profil))
                .andExpect(view().name("admin/profilEdit"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("post Name ändern")
    void post_Name() throws Exception {
        mockMvc.perform(post("/admin/profilEdit/1")
                .param("name", "test"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("post Name ändern")
    void post_Name_BackEnd() throws Exception {
        mockMvc.perform(post("/admin/profilEdit/1")
                        .param("name", "test"));
        verify(editor).editName("1", "test");
    }
}