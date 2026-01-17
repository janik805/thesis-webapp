package com.awesome.thesis.logic.application.service.fachgebiete;

import com.awesome.thesis.logic.application.service.profiles.IProfileRepo;
import com.awesome.thesis.logic.application.service.themen.IThemaRepo;
import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

class FachgebieteEditorTest {
    @Test
    @DisplayName("A new fachgebiet can be added")
    void test_add() {
        //Arrange
        IFachgebieteRepo repo = mock(IFachgebieteRepo.class);
        when(repo.contains(anyString())).thenReturn(false);
        IProfileRepo profilRepo = mock(IProfileRepo.class);
        IThemaRepo themaRepo = mock(IThemaRepo.class);
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
        IFachgebieteRepo repo = mock(IFachgebieteRepo.class);
        when(repo.contains(anyString())).thenReturn(true);
        IProfileRepo profilRepo = mock(IProfileRepo.class);
        IThemaRepo themaRepo = mock(IThemaRepo.class);
        FachgebieteEditor editor = new FachgebieteEditor(repo, profilRepo, themaRepo);

        //Act
        editor.add("test");

        //Assert
        verify(repo, never()).add(any());
    }
}