package com.awesome.thesis.controller.betreuende;


import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(BetreuendeThemaCreateController.class)
public class BetreuendeThemaCreateControllerTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    ThemaEditor themaEditor;

    @MockitoBean
    ProfilEditor profilEditor;

    @Test
    @WithMockOAuth2User()
    @DisplayName("Die themaCreate Seite ist erreichbar")
    void test_1() throws Exception {
                mvc.perform(get("/thema/create"))
                .andExpect(status().isOk());
    }


}
