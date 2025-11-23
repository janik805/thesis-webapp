package com.awesome.thesis.profiles;

import com.awesome.thesis.profiles.profil.Profil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfilEditorTest {
    @Test
    @DisplayName("a non existing Profil that's saved to the database gets an id")
    void testId() {
        //Arrange
        Profil profil = new Profil("test");
        Profile profile = mock(Profile.class);
        when(profile.containsKey(any())).thenReturn(true);
        when(profile.save(any(Profil.class))).thenReturn("id");
        ProfilEditor editor = new ProfilEditor(profile);

        //Act
        editor.add(profil);

        //Assert
        assertThat(profil.getId()).isEqualTo("id");
    }

    @Test
    @DisplayName("an existing Profil that's saved gets updated")
    void testUpdate() {
        //Arrange
        Profil profil = new Profil("test");
        profil.setId("id");
        Profile profile = mock(Profile.class);
        when(profile.containsKey(any())).thenReturn(true);
        ProfilEditor editor = new ProfilEditor(profile);

        //Act
        editor.add(profil);

        //Assert
        verify(profile).update(any(), any(Profil.class));
    }

    @Test
    @DisplayName("an existing Profil can be loaded from the database")
    void testGet() {
        //Arrange
        Profile profile = mock(Profile.class);
        when(profile.containsKey(any())).thenReturn(true);
        ProfilEditor editor = new ProfilEditor(profile);

        //Act
        editor.get("id");

        //Assert
        verify(profile).get("id");
    }

    @Test
    @DisplayName("a non existing Profile can't be loaded from the Database")
    void testGetNotFound() {
        //Arrange
        Profile profile = mock(Profile.class);
        when(profile.containsKey(any())).thenReturn(false);
        ProfilEditor editor = new ProfilEditor(profile);

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> editor.get("id"));
    }
}