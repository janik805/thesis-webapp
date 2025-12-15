package com.awesome.thesis.controller;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.files.DateiTypPruefer;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(DateiController.class)
class DateiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockOAuth2User()
    void get_auf_upload() throws Exception {
        mockMvc.perform(get("/upload"))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"));
    }

    @Test
    @WithMockOAuth2User()
    void keine_DateiInfo_wird_erstellt_wenn_Datentyp_nicht_stimmt() throws Exception {
        MockMultipartFile file = new MockMultipartFile("datei", "test".getBytes());
        MockedStatic<DateiTypPruefer> mock = mockStatic(DateiTypPruefer.class);
        mock.when(() -> DateiTypPruefer.verify(any())).thenReturn(false);

        mockMvc.perform(multipart("/upload").file(file)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("dateiInfos"));
    }

}