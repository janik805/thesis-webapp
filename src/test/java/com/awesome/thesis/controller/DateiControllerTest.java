package com.awesome.thesis.controller;

import com.awesome.thesis.files.DateiTypPruefer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DateiController.class)
class DateiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void get_auf_upload() throws Exception {
        mockMvc.perform(get("/upload"))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"));
    }

    @Test
    void keine_DateiInfo_wird_erstellt_wenn_Datentyp_nicht_stimmt() throws Exception {
        MockMultipartFile file = new MockMultipartFile("datei", "test".getBytes());
        MockedStatic<DateiTypPruefer> mock = mockStatic(DateiTypPruefer.class);
        mock.when(() -> DateiTypPruefer.verify(any())).thenReturn(false);

        mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("dateiInfos"));
    }

}