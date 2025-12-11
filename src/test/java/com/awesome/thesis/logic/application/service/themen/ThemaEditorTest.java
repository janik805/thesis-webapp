package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ThemaEditorTest {

    private static Thema neuesThema() {
        return new Thema("Test", "Beschreibung");
    }


    @Test
    @DisplayName("When a new Link gets added, its saved in the repository")
    void test_1() {
        Thema thema = neuesThema();
        IThemaRepo repo = mock(IThemaRepo.class);
        when(repo.get("a")).thenReturn(thema);
        when(repo.containsKey("a")).thenReturn(true);
        ThemaEditor editor = new ThemaEditor(repo);
        Link link = mock(Link.class);

        //Act
        editor.addLink("a", link);

        //Assert
        verify(repo).update(eq("a"), any(Thema.class));
        assertThat(thema.getLinks()).contains(link);
    }

    @Test
    @DisplayName("When you try to add a link to a Thema with non existent id, an expcetion gets thrown")
    void test_1_5(){
        //Arramge
        Thema thema = neuesThema();
        IThemaRepo repo = mock(IThemaRepo.class);
        ThemaEditor editor = new ThemaEditor(repo);
        Link link = mock(Link.class);

        //Act && Assert
        assertThrows(NoSuchElementException.class, () -> editor.addLink("nonExistentId", link));

    }

    @Test
    @DisplayName("When a link gets removed it is no longer in the repository")
    void test_2() {
        //Arrange
        Thema thema = neuesThema();
        IThemaRepo repo = mock(IThemaRepo.class);
        when(repo.get("a")).thenReturn(thema);
        ThemaEditor editor = new ThemaEditor(repo);
        when(repo.containsKey("a")).thenReturn(true);
        Link link = mock(Link.class);
        editor.addLink("a", link);
        //Act
        editor.removeLink("a", link);

        //Assert
        verify(repo, times(2)).update(eq("a"), any(Thema.class));
        assertThat(thema.getLinks()).doesNotContain(link);
    }

    @Test
    @DisplayName("When setTitel is called, the title is actually changed")
    void test_3() {
        //Arrange
        Thema thema = neuesThema();
        IThemaRepo repo = mock(IThemaRepo.class);
        when(repo.get("a")).thenReturn(thema);
        ThemaEditor editor = new ThemaEditor(repo);
        when(repo.containsKey("a")).thenReturn(true);

        //Act
        editor.editTitel("a","Hallo");

        //Assert
        assertThat(thema.getTitel()).isEqualTo("Hallo");
    }

    @Test
    @DisplayName("When setTitel is called with an empty title, an IllegalArgumentException gets thrown")
    void test_4() {
        //Arrange
        Thema thema = neuesThema();
        IThemaRepo repo = mock(IThemaRepo.class);
        when(repo.get("a")).thenReturn(thema);
        ThemaEditor editor = new ThemaEditor(repo);
        when(repo.containsKey("a")).thenReturn(true);

        //Act && Assert
        assertThrows(IllegalArgumentException.class, () -> editor.editTitel("a", ""));
    }
    @Test
    @DisplayName("When setTitel is called on a Thema with non existent id in the repository, an exception gets thrown")
    void test_4_5() {
        //Arrange
        Thema thema = neuesThema();
        IThemaRepo repo = mock(IThemaRepo.class);
        ThemaEditor editor = new ThemaEditor(repo);

        //Act && Assert
        assertThrows(NoSuchElementException.class, () -> editor.editTitel("nonExistentId", "titel"));
    }



    @Test
    @DisplayName("When addThema gets called with a Thema which is already saved, the Thema Object gets updated")
    void test_5() {
        //Arrange
        Thema thema = neuesThema();
        Thema thema2 = neuesThema();
        thema2.setId("a");
        IThemaRepo repo = mock(IThemaRepo.class);
        when(repo.get("a")).thenReturn(thema);
        ThemaEditor editor = new ThemaEditor(repo);
        when(repo.containsKey("a")).thenReturn(true);

        //Act
        editor.addThema(thema2);

        //Assert
        verify(repo).update(any(), any(Thema.class));
    }

    @Test
    @DisplayName("When addThema gets called with a Thema which has never been saved, it gets an id")
    void test_6() {
        //Arrange
        Thema thema = neuesThema();
        IThemaRepo repo = mock(IThemaRepo.class);
        ThemaEditor editor = new ThemaEditor(repo);
        when(repo.save(any())).thenReturn("a");

        //Act
        editor.addThema(thema);

        //Assert
        assertThat(thema.getId()).isEqualTo("a");
    }

    @Test
    @DisplayName("When getThema is called with an id that exists, it returns the correct thema")
    void test_7() {
        //Arrange
        Thema thema = neuesThema();
        IThemaRepo repo = mock(IThemaRepo.class);
        when(repo.containsKey(any())).thenReturn(true);
        ThemaEditor editor = new ThemaEditor(repo);

        //Act
        editor.getThema("id");

        //Assert
        verify(repo).get("id");
    }

    @Test
    @DisplayName("When getThema is called with an id that does not exist in the database, a NoSuchElementException gets thrown")
    void test_8() {
        //Arrange
        Thema thema = neuesThema();
        IThemaRepo repo = mock(IThemaRepo.class);
        when(repo.containsKey(any())).thenReturn(false);
        ThemaEditor editor = new ThemaEditor(repo);

        //Act && Assert
        assertThrows(NoSuchElementException.class, () -> editor.getThema("a"));
    }

}
