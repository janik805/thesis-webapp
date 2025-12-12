package com.awesome.thesis.controller;

import com.awesome.thesis.controller.admin.ThemaEditorController;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ThemaEditorController.class)
public class ThemaEditorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    ThemaEditor themaEditor;

    @Test
    @DisplayName("Tests that admin/themaEdit is rechable")
    void test_1() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema(any())).thenReturn(thema);
        mvc.perform(get("/editThema/propra"))
                .andExpect(status().isOk());
    }
}
