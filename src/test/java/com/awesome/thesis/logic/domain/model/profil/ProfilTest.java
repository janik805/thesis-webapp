package com.awesome.thesis.logic.domain.model.profil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProfilTest {
    Profil profil;

    @BeforeEach
    void set_up() {
        profil = new Profil(1, "test");
    }

    @Test
    @DisplayName("fachgebieteString returns all fachgebiete as a String")
    void test_fachgebieteString() {
        //Arrange
        profil.addFachgebiet("fachgebiet1");
        profil.addFachgebiet("fachgebiet2");

        //Act
        String r = profil.fachgebieteString();

        //Assert
        assertThat(r).contains("fachgebiet1");
        assertThat(r).contains("fachgebiet2");
    }

    @Test
    @DisplayName("fachgebieteString returns empty String if fachgebiete is empty")
    void test_fachgebieteStringEmpty() {
        //Act
        String r = profil.fachgebieteString();

        //Assert
        assertThat(r).isEmpty();
    }
}