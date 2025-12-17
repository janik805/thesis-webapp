package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.security.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.controller.admin.ThemaEditorController;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(ThemaEditorController.class)
public class ThemaEditorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    ThemaEditor editor;

    @Test
    @DisplayName("Tests that admin/themaEdit is reachable")
    @WithMockOAuth2User()
    void test_1() throws Exception {
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(get("/themaEdit/propra"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Shows that /editThema/{id} can be displayed")
    @WithMockOAuth2User()
    void test_2() throws Exception {
        Thema thema = mock(Thema.class);
            when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(get("/themaEdit/propra"))
                    .andExpect(model().attribute("thema", thema))
                    .andExpect(view().name("admin/themaEdit"))
                    .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Shows that the title can be changed")
    @WithMockOAuth2User()
    void test_3() throws Exception {
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(post("/themaEdit/propra/editInfo").param("titel", "Changed Titel")
                .with(csrf()));
        verify(editor).editTitel("propra", "Changed Titel");
    }

    @Test
    @DisplayName("Shows that the description can be changed")
    @WithMockOAuth2User()
    void test_4() throws Exception {
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(post("/themaEdit/propra/editInfo").param("titel", "egal").param("beschreibung", "egal")
                .with(csrf()));
        verify(editor).editBeschreibung("propra", "egal");
    }

    @Test
    @DisplayName("Shows that a link can be added")
    @WithMockOAuth2User()
    void test_5() throws Exception {
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(post("/themaEdit/propra/editLink").param("url", "https://www.google.com/").param("urlBeschreibung", "egal")
                .with(csrf()));
        verify(editor).addLink("propra", "https://www.google.com/", "egal");
    }

    @Test
    @DisplayName("Specific links can be deleted")
    @WithMockOAuth2User()
    void test_6() throws Exception{
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(post("/themaEdit/propra/deleteLink")
                        .param("url", "https://www.google.com/")
                        .param("text","Google als Beispiel" )
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        verify(editor).removeLink("propra", new Link("https://www.google.com/", "Google als Beispiel"));

    }

    @Test
    @DisplayName("You cant set an empty title for the Thema object")
    @WithMockOAuth2User()
    void test_7() throws Exception {
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(post("/themaEdit/propra/editInfo").param("titel", "").param("beschreibung", "egal")
                .with(csrf()));
        verify(editor, never()).editTitel("propra", "");
    }

    @Test
    @DisplayName("You cant add an empty link")
    @WithMockOAuth2User()
    void test_8() throws Exception {
        Thema thema = mock(Thema.class);
        when(editor.getThema(any())).thenReturn(thema);
        mvc.perform(post("/themaEdit/propra/editLink").param("url", "").param("urlBeschreibung", "egal")
                .with(csrf()));
        verify(editor, never()).addLink("propra", "", "egal");
    }

}
