package com.awesome.thesis.logic.application.service.fachgebiete;

import com.awesome.thesis.logic.application.service.profiles.ProfileRepoI;
import com.awesome.thesis.logic.application.service.themen.IThemaRepo;
import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class FachgebieteEditorTest {
    IFachgebieteRepo repo;
    ProfileRepoI profilRepo;
    IThemaRepo themaRepo;

    @BeforeEach
    void dependencies() {
        repo = mock(IFachgebieteRepo.class);
        profilRepo = mock(ProfileRepoI.class);
        themaRepo = mock(IThemaRepo.class);
    }

    @Test
    @DisplayName("A new fachgebiet can be added")
    void test_add() {
        //Arrange
        when(repo.contains(anyString())).thenReturn(false);
        FachgebieteEditor editor = new FachgebieteEditor(repo, profilRepo, themaRepo);

        //Act
        editor.add("test");

        //Assert
        verify(repo).add(new Fachgebiet("test"));
    }

    @Test
    @DisplayName("An existing fachgebiet can't be added")
    void test_addExists() {
        //Arrange
        when(repo.contains(anyString())).thenReturn(true);
        FachgebieteEditor editor = new FachgebieteEditor(repo, profilRepo, themaRepo);

        //Act
        editor.add("test");

        //Assert
        verify(repo, never()).add(any());
    }

    @Test
    @DisplayName("getAll gets Fachgebiete from repo")
    void test_getAll() {
        //Arrange
        FachgebieteEditor editor = new FachgebieteEditor(repo, profilRepo, themaRepo);

        //Act
        editor.getAll();

        //Assert
        verify(repo).getAll();
    }

    @Test
    @DisplayName("An unused fachgebiet can be removed")
    void test_remove() {
        //Arrange
        when(profilRepo.getAll()).thenReturn(new ArrayList<>());
        when(themaRepo.getThemen()).thenReturn(new ArrayList<>());
        FachgebieteEditor editor = new FachgebieteEditor(repo, profilRepo, themaRepo);

        //Act
        editor.remove("test");

        //Assert
        verify(repo).delete("test");
    }

    @Test
    @DisplayName("An used fachgebiet by profile can't be removed")
    void test_removeFachgebietProfil() {
        //Arrange
        Profil profil = mock(Profil.class);
        when(profil.hasFachgebiet(any())).thenReturn(true);
        when(profilRepo.getAll()).thenReturn(List.of(profil));
        when(themaRepo.getThemen()).thenReturn(new ArrayList<>());
        FachgebieteEditor editor = new FachgebieteEditor(repo, profilRepo, themaRepo);

        //Act
        editor.remove("test");

        //Assert
        verify(repo, never()).delete(any());
    }

    @Test
    @DisplayName("An used fachgebiet by themen can't be removed")
    void test_removeFachgebietThema() {
        //Arrange
        Thema thema = mock(Thema.class);
        when(thema.hasFachgebiet(any())).thenReturn(true);
        when(profilRepo.getAll()).thenReturn(new ArrayList<>());
        when(themaRepo.getThemen()).thenReturn(List.of(thema));
        FachgebieteEditor editor = new FachgebieteEditor(repo, profilRepo, themaRepo);

        //Act
        editor.remove("test");

        //Assert
        verify(repo, never()).delete(any());
    }
}