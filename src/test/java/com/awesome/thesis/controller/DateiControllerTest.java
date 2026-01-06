package com.awesome.thesis.controller;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.files.DateiService;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(DateiController.class)
class DateiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProfilEditor editor;

    @MockitoBean
    DateiService dateiService;

    @Test
    @WithMockOAuth2User()
    void get_auf_upload() throws Exception {
        mockMvc.perform(get("/datei/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"));
    }



}