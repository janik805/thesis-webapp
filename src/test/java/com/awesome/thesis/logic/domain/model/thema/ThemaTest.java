package com.awesome.thesis.logic.domain.model.thema;

import static org.assertj.core.api.Assertions.assertThat;

import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaFachgebiet;
import com.awesome.thesis.logic.domain.model.themen.ThemaVoraussetzung;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Thema Aggregate Root.
 */

public class ThemaTest {
  Thema thema;

  @BeforeEach
  public void themaSet() {
    thema = new Thema("Test", 180645494);
  }

  @Test
  @DisplayName("fitsRequirements returns true if the input is null")
  void test_1() {
    //Act && Assert
    assertThat(thema.fitsRequirements(null, null)).isTrue();
  }

  @Test
  @DisplayName("fitsRequirements returns true "
      + "if Voraussetzungen is null and the Thema contains all Fachgebiete")
  void test_2() {
    //Arrange
    thema.addFachgebiet(new ThemaFachgebiet("propra"));

    //Act && Assert
    assertThat(thema.fitsRequirements(null, Set.of(new ThemaFachgebiet("propra")))).isTrue();
  }

  @Test
  @DisplayName("fitsRequirements returns true "
      + "if Interessen is null and the Thema contains all Voraussetzungen")
  void test_3() {
    //Act && Assert
    thema.updateVoraussetzungen(Set.of(new ThemaVoraussetzung("propra")));
    assertThat(thema.fitsRequirements(Set.of(new ThemaVoraussetzung("propra")), null)).isTrue();
  }

  @Test
  @DisplayName("fitsRequirements returns true"
      + " if the Thema contains all Voraussetzungen and Interessen")
  void test_4() {
    //Arrange
    thema.updateVoraussetzungen(Set.of(new ThemaVoraussetzung("propra")));
    thema.addFachgebiet(new ThemaFachgebiet("propra2"));

    //Act && Assert
    assertThat(thema.fitsRequirements(Set.of(new ThemaVoraussetzung("propra")),
        Set.of(new ThemaFachgebiet("propra2"))))
        .isTrue();
  }

  @Test
  @DisplayName("fitsRequirements returns false,"
      + " if the Thema does not contain all Voraussetzungen and Interessen")
  void test_5() {
    //Act && Assert
    thema.updateVoraussetzungen(Set.of(new ThemaVoraussetzung("propra")));
    thema.addFachgebiet(new ThemaFachgebiet("propra2"));
    assertThat(thema.fitsRequirements(Set.of(new ThemaVoraussetzung("propra")),
        Set.of(new ThemaFachgebiet("mafin3"))))
        .isFalse();
  }

  @Test
  @DisplayName("calcRang returns 0 if all input is null")
  void test_6() {
    //Act && Assert
    long number = thema.calcRang(null, null);
    assertThat(number).isEqualTo(0);
  }

  @Test
  @DisplayName("calcRang returns 0 if Voraussetzungen is null "
      + "and Interessen isnt, and the Thema does not have any Voraussetzungen")
  void test_7() {
    //Act && Assert
    long number = thema.calcRang(null, Set.of(new ThemaFachgebiet("propra")));
    assertThat(number).isEqualTo(0);
  }

  @Test
  @DisplayName("calcRang returns -1 "
      + "if Voraussetzungen of the input does not contain all Voraussetzungen in the Thema")
  void test_8() {
    //Arrange
    thema.updateVoraussetzungen(Set.of(new ThemaVoraussetzung("mafin3")));

    //Act
    long number = thema.calcRang(Set.of(new ThemaVoraussetzung("propra")),
        Set.of(new ThemaFachgebiet("propra2")));

    //Assert
    assertThat(number).isEqualTo(-1);
  }

  @Test
  @DisplayName("calcRang returns the correct number"
      + " if Thema contains all Voraussetzungen of the Input and Interessen is null")
  void test_9() {
    //Arrange
    thema.updateVoraussetzungen(Set.of(new ThemaVoraussetzung("mafin3")));

    //Act
    long number = thema.calcRang(Set.of(new ThemaVoraussetzung("mafin3")), null);

    //Assert
    assertThat(number).isEqualTo(1L);
  }

  @Test
  @DisplayName("If the Thema contains all Voraussetzungen and Interessen of the input,"
      + " the amount of matching interessen is returned")
  void test_10() {
    //Arrange
    thema.updateVoraussetzungen(Set.of(new ThemaVoraussetzung("mafin3")));
    thema.addFachgebiet(new ThemaFachgebiet("propra"));
    thema.addFachgebiet(new ThemaFachgebiet("propra2"));

    //Act
    long number = thema.calcRang(Set.of(new ThemaVoraussetzung("mafin3")),
        Set.of(new ThemaFachgebiet("propra"), new ThemaFachgebiet("propra2")));

    //Assert
    assertThat(number).isEqualTo(2L);
  }

  @Test
  @DisplayName("hasBeschreibung retruns false if beschreibung is null")
  void test_11() {
    //Act
    boolean result = thema.hasBeschreibung();

    //Assert
    assertThat(result).isFalse();
  }

}
