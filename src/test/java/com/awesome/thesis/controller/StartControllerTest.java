package com.awesome.thesis.controller;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.controller.admin.AdminProfilCreator;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(StartController.class)
class StartControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProfilEditor editor;

    @Test
    @WithMockUser()
    @DisplayName("Ein unangemeldeter Nutzer kann die Start Seite aufrufen")
    void get_createProfil() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}