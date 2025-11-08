package com.awesome.thesis.profiles;

import com.awesome.thesis.profiles.profil.Profil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProfileTest {
    @Test
    @DisplayName("a non existing Profil that's saved to the database gets an id")
    void testId() {
        //Arrange
        Profil profil = new Profil("test");
        Database database = mock(Database.class);
        when(database.containsKey(anyLong())).thenReturn(true);
        when(database.save(any(Profil.class))).thenReturn(1L);
        Profile profile = new Profile(database);

        //Act
        profile.save(profil);

        //Assert
        assertThat(profil.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("an existing Profil that's saved gets updated")
    void testUpdate() {
        //Arrange
        Profil profil = new Profil("test");
        profil.setId(1L);
        Database database = mock(Database.class);
        when(database.containsKey(anyLong())).thenReturn(true);
        Profile profile = new Profile(database);

        //Act
        profile.save(profil);

        //Assert
        verify(database).update(anyLong(), any(Profil.class));
    }

    @Test
    @DisplayName("an existing Profil can be loaded from the database")
    void testGet() {
        //Arrange
        Database database = mock(Database.class);
        when(database.containsKey(anyLong())).thenReturn(true);
        Profile profile = new Profile(database);

        //Act
        profile.get(1L);

        //Assert
        verify(database).get(1L);
    }
}