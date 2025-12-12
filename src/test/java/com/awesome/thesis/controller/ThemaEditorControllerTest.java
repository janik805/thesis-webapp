package com.awesome.thesis.controller;

import com.awesome.thesis.controller.admin.ThemaEditorController;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ThemaEditorController.class)
public class ThemaEditorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    ThemaEditor editor;

    @Test
    @DisplayName("Tests that admin/themaEdit is rechable")
    void test_1() throws Exception {
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(get("/editThema/propra"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Shows that /editThema/{id} can be displayed")
    void test_2() throws Exception {
        Thema thema = mock(Thema.class);
            when(editor.getThema(any())).thenReturn(thema);
            mvc.perform(get("/editThema/propra"))
                    .andExpect(model().attribute("thema", thema))
                    .andExpect(view().name("admin/themaEdit"))
                    .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Shows that the title can be changed")
    void test_3() throws Exception {
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(post("/themaEdit/propra/editInfo").param("titel", "Changed Titel"));
        verify(editor).editTitel("propra", "Changed Titel");
    }

    @Test
    @DisplayName("Shows that the description can be changed")
    void test_4() throws Exception {
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(post("/themaEdit/propra/editInfo").param("beschreibung", "egal"));
        verify(editor).editBeschreibung("propra", "egal");
    }

    @Test
    @DisplayName("Shows that a link can be added")
    void test_5() throws Exception {
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(post("/themaEdit/propra/editLink").param("url", "egal").param("urlBeschreibung", "egal"));
        verify(editor).addLink("propra", new Link("egal", "egal"));
    }

}
