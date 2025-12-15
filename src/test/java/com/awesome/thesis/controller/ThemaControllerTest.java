package com.awesome.thesis.controller;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(ThemaController.class)
public class ThemaControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    ThemaEditor themaEditor;

    @Test
    @WithMockOAuth2User()
    @DisplayName("themaListe is accessible")
    void test_1() throws Exception{
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema(any())).thenReturn(thema);
        mvc.perform(get("/themenListe"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User()
    @DisplayName("thema is accessible")
    void test_2() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema(any())).thenReturn(thema);
        mvc.perform(get("/thema/propra"))
                .andExpect(model().attribute("thema", thema))
                .andExpect(status().isOk());
    }
}
