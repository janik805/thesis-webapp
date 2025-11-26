package com.awesome.thesis.profiles;

import com.awesome.thesis.thema.Thema;
import com.awesome.thesis.thema.ThemaRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ThemaRepositoryTest {

    @Test
    @DisplayName("If a Thema with the given title exists, it will be returned")
    void test_1() {
        Thema thema = mock(Thema.class);
        when(thema.getTitel()).thenReturn("titel");
        ThemaRepositoryImpl repository = new ThemaRepositoryImpl();
        repository.addThema(thema);
        assertThat(repository.getThema("titel")).isEqualTo(thema);
    }
    @Test
    @DisplayName("If no Thema with given titel exists, a NoSuchElementException gets thrown")
    void test_2() {
        Thema thema = mock(Thema.class);
        when(thema.getTitel()).thenReturn("titel");
        ThemaRepositoryImpl repository = new ThemaRepositoryImpl();
        repository.addThema(thema);
        assertThrows(NoSuchElementException.class, () -> repository.getThema("other titel"));
    }
}
