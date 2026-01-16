package com.awesome.thesis.controller;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(MatchingController.class)
class MatchingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProfilEditor profilEditor;

    @MockitoBean
    FachgebieteEditor fachgebieteEditor;

    @MockitoBean
    VoraussetzungenEditor vorEditor;

    @MockitoBean
    ThemaEditor themaEditor;

    @Test
    @WithMockOAuth2User()
    @DisplayName("get auf /matching")
    void test_getProfilId() throws Exception {
        mockMvc.perform(get("/matching"))
                .andExpect(status().isOk());
    }
}