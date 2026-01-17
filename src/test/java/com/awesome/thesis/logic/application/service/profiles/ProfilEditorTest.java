package com.awesome.thesis.logic.application.service.profiles;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProfilEditorTest {
    IProfileRepo profile;
    FachgebieteEditor fachgebieteEditor;

    @BeforeEach
    void dependencies() {
        profile = mock(IProfileRepo.class);
        fachgebieteEditor = mock(FachgebieteEditor.class);
    }

    @Test
    @DisplayName("getAll() returns all profiles of Repo")
    void test_getAll() {
        //Arrange
        List<Profil> p = List.of(new Profil(1, "test"));
        when(profile.getAll()).thenReturn(p);
        ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);

        //Act + Assert
        assertThat(editor.getAll()).isEqualTo(p);
    }

    @Test
    @DisplayName("an existing Profil can be loaded from the database")
    void test_get() {
        //Arrange
        when(profile.containsKey(anyInt())).thenReturn(true);
        ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);

        //Act
        editor.get(1);

        //Assert
        verify(profile).get(1);
    }

    @Test
    @DisplayName("an existing Profil can be loaded from the database")
    void test_getProfil() {
        //Arrange
        when(profile.containsKey(anyInt())).thenReturn(true);
        Profil p = new Profil(1, "test");
        when(profile.get(anyInt())).thenReturn(p);
        ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);

        //Act + Assert
        assertThat(editor.get(1)).isEqualTo(p);
    }

    @Test
    @DisplayName("a non existing Profile can't be loaded from the Database")
    void test_getNotFound() {
        //Arrange
        when(profile.containsKey(anyInt())).thenReturn(false);
        ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> editor.get(1));
    }
}