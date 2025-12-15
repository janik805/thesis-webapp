package com.awesome.thesis.controller;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(ProfilController.class)
class ProfilControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProfilEditor editor;

    @Test
    @WithMockOAuth2User()
    @DisplayName("get auf /profil/{id}")
    void test_getProfilId() throws Exception {
        Profil profil = mock(Profil.class);
        when(editor.get(any())).thenReturn(profil);
        mockMvc.perform(get("/profil/1"))
                .andExpect(model().attribute("profil", profil))
                .andExpect(view().name("profiles/profil"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User()
    @DisplayName("get auf /profile")
    void test_getProfile() throws Exception {
        List<Profil> profile = new ArrayList<>();
        when(editor.getAll()).thenReturn(profile);
        mockMvc.perform(get("/profile"))
                .andExpect(model().attribute("profile", profile))
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/profile"));
    }
}