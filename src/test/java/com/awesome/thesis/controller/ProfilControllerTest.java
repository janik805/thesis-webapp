package com.awesome.thesis.controller;

import com.awesome.thesis.profiles.ProfilEditor;
import com.awesome.thesis.profiles.profil.Profil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfilController.class)
class ProfilControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProfilEditor editor;

    @Test
    @DisplayName("get auf /profil/{id}")
    void test_getProfilId() throws Exception {
        Profil profil = mock(Profil.class);
        when(editor.get(any())).thenReturn(profil);
        mockMvc.perform(get("/profil/1"))
                .andExpect(model().attribute("profil", profil))
                .andExpect(status().isOk());
    }
}