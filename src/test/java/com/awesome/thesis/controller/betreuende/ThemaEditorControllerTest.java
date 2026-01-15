package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.configurations.AppUserService;
import com.awesome.thesis.configurations.MethodSecurityConfig;
import com.awesome.thesis.configurations.SecurityConfig;
import com.awesome.thesis.helper.WithMockOAuth2User;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaLink;
import com.awesome.thesis.logic.domain.model.themen.Voraussetzung;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfig.class, MethodSecurityConfig.class, AppUserService.class})
@WebMvcTest(BetreuendeThemaEditorController.class)
public class ThemaEditorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    ThemaEditor themaEditor;

    @MockitoBean
    ProfilEditor editor;

    @MockitoBean
    VoraussetzungenEditor vorEditor;

    @Test
    @DisplayName("Tests that /themaEdit is reachable")
    @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
    void test_1() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema("2")).thenReturn(thema);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        mvc.perform(get("/themaEdit/2"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Shows that the title can be changed")
    @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
    void test_3() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        when(themaEditor.getThema("2")).thenReturn(thema);
        mvc.perform(post("/themaEdit/2/editInfo").param("titel", "Changed Titel")
                .with(csrf()));
        verify(themaEditor).editTitel(1,"2", "Changed Titel");
    }

    @Test
    @DisplayName("Shows that the description can be changed")
    @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
    void test_4() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema("2")).thenReturn(thema);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        mvc.perform(post("/themaEdit/2/editInfo").param("titel", "egal").param("beschreibung", "egal")
                .with(csrf()));
        verify(themaEditor).editBeschreibung("2", "egal");
    }

    @Test
    @DisplayName("Shows that a link can be added")
    @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
    void test_5() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema("2")).thenReturn(thema);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        mvc.perform(post("/themaEdit/2/editLink").param("url", "https://www.google.com/").param("urlBeschreibung", "egal")
                .with(csrf()));
        verify(themaEditor).addLink("2", "https://www.google.com/", "egal");
    }

    @Test
    @DisplayName("Specific links can be deleted")
    @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
    void test_6() throws Exception{
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema("2")).thenReturn(thema);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        mvc.perform(post("/themaEdit/2/deleteLink")
                        .param("url", "https://www.google.com/")
                        .param("text","Google als Beispiel" )
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        verify(themaEditor).removeLink("2", new ThemaLink("https://www.google.com/", "Google als Beispiel"));

    }

    @Test
    @DisplayName("You cant set an empty title for the Thema object")
    @WithMockOAuth2User()
    void test_7() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema("2")).thenReturn(thema);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        mvc.perform(post("/themaEdit/2/editInfo").param("titel", "").param("beschreibung", "egal")
                .with(csrf()));
        verify(themaEditor, never()).editTitel(1, "2", "");
    }

    @Test
    @DisplayName("You cant add an empty link")
    @WithMockOAuth2User()
    void test_8() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema("2")).thenReturn(thema);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        mvc.perform(post("/themaEdit/2/editLink").param("url", "").param("urlBeschreibung", "egal")
                .with(csrf()));
        verify(themaEditor, never()).addLink("2", "", "egal");
    }

    @Test
    @DisplayName("You can edit Voraussetzungen")
    @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
    void test_9() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema(any())).thenReturn(thema);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        mvc.perform(post("/themaEdit/2/editVoraussetzung").param( "voraussetzungen","bob")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());;
        verify(themaEditor, times(1)).updateVoraussetzungen("2", Set.of(new Voraussetzung("bob")));
    }

    @Test
    @DisplayName("You can remove a Thema object")
    @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
    void test_11() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema(any())).thenReturn(thema);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        mvc.perform(post("/thema/2/deleteThema").with(csrf()));
        verify(themaEditor).deleteThema("2", 1);
    }

    @Test
    @DisplayName("You can add a Fachgebiet")
    @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
    void test_12() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema(any())).thenReturn(thema);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        mvc.perform(post("/themaEdit/2/addFachgebiet").param("fachgebiet", "das").with(csrf()));
        verify(themaEditor).addFachgebiet("2","das");
    }

    @Test
    @DisplayName("You can remove a Fachgebiet")
    @WithMockOAuth2User(roles = {"BETREUENDE"}, id = 1)
    void test_13() throws Exception {
        Thema thema = mock(Thema.class);
        when(themaEditor.getThema(any())).thenReturn(thema);
        when(themaEditor.allowedEdit(anyLong(), any())).thenReturn(true);
        mvc.perform(post("/themaEdit/2/removeFachgebiet").param("fachgebiet", "das").with(csrf()));
        verify(themaEditor).removeFachgebiet("2","das");
    }
}
