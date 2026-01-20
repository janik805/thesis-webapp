package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ThemaEditorTest {

    private static Thema neuesThema() {
        return new Thema("Test", 180645494);
    }
    private IThemaRepo repo;
    private ProfilEditor profilEditor;
    private ThemaEditor editor;
    private FachgebieteEditor fachEditor;

    @BeforeEach
    void setUp() {
        repo = mock(IThemaRepo.class);
        profilEditor = mock(ProfilEditor.class);
        fachEditor = mock(FachgebieteEditor.class);
        editor = new ThemaEditor(repo, profilEditor, fachEditor);
    }

    @Test
    @DisplayName("When a new Link gets added, its saved in the repository")
    void test_1() {
        Thema thema = neuesThema();

        when(repo.get(2)).thenReturn(thema);
        when(repo.containsKey(2)).thenReturn(true);
        ThemaLink link = new ThemaLink("url", "beschreibung");

        //Act
        editor.addLink(2, "url", "beschreibung");

        //Assert
        verify(repo).update(eq(2), any(Thema.class));
        assertThat(thema.getLinks()).contains(link);
    }

    @Test
    @DisplayName("When you try to add a link to a Thema with non existent id, an expcetion gets thrown")
    void test_1_5(){
        //Act && Assert
        assertThrows(NoSuchElementException.class, () -> editor.addLink(2, "url", "beschreibung"));

    }

    @Test
    @DisplayName("When a link gets removed it is no longer in the repository")
    void test_2() {
        //Arrange
        Thema thema = neuesThema();
        when(repo.get(2)).thenReturn(thema);
        when(repo.containsKey(2)).thenReturn(true);
        ThemaLink link = new ThemaLink("url", "beschreibung");
        editor.addLink(2, "url", "beschreibung");
        //Act
        editor.removeLink(2, link);

        //Assert
        verify(repo, times(2)).update(eq(2), any(Thema.class));
        assertThat(thema.getLinks()).doesNotContain(link);
    }

    @Test
    @DisplayName("When setTitel is called, the title is actually changed")
    void test_3() {
        //Arrange
        Thema thema = neuesThema();
        when(repo.get(2)).thenReturn(thema);
        when(repo.containsKey(2)).thenReturn(true);

        //Act
        editor.editTitel(thema.getProfilId(), 2,"Hallo");

        //Assert
        assertThat(thema.getTitel()).isEqualTo("Hallo");
    }

    @Test
    @DisplayName("When addThema gets called with a Thema which is already saved, the Thema Object gets updated")
    void test_5() {
        //Arrange
        Thema thema = neuesThema();
        thema.setId(2);
        when(repo.containsKey(2)).thenReturn(true);

        //Act
        editor.addThema(thema, 1);

        //Assert
        verify(repo).update(anyInt(), any(Thema.class));
    }

    @Test
    @DisplayName("When getThema is called with an id that exists, it returns the correct thema")
    void test_7() {
        //Arrange
        when(repo.containsKey(anyInt())).thenReturn(true);

        //Act
        editor.getThema(2);

        //Assert
        verify(repo).get(2);
    }

    @Test
    @DisplayName("When getThema is called with an id that does not exist in the database, a NoSuchElementException gets thrown")
    void test_8() {
        //Arrange
        when(repo.containsKey(anyInt())).thenReturn(false);

        //Act && Assert
        assertThrows(NoSuchElementException.class, () -> editor.getThema(2));
    }

}
