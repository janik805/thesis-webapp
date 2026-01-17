package com.awesome.thesis.logic.application.service.profiles;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("an existing Profil can be loaded from the database")
    void testGet() {
        //Arrange
        when(profile.containsKey(anyInt())).thenReturn(true);
        ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);

        //Act
        editor.get(1);

        //Assert
        verify(profile).get(1);
    }

    @Test
    @DisplayName("a non existing Profile can't be loaded from the Database")
    void testGetNotFound() {
        //Arrange
        when(profile.containsKey(anyInt())).thenReturn(false);
        ProfilEditor editor = new ProfilEditor(profile, fachgebieteEditor);

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> editor.get(1));
    }
}