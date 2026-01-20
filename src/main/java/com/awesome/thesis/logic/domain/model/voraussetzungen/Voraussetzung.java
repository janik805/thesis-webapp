package com.awesome.thesis.logic.domain.model.voraussetzungen;

import com.awesome.thesis.annotations.AggregateRoot;
import java.util.Objects;
import org.springframework.data.annotation.Id;

/**
 * Aggregate Root der Voraussetzung.
 */
@AggregateRoot
public class Voraussetzung {

  @Id
  private final String voraussetzung;

  /**
   * Initialisiert die Voraussetzung.
   * @param voraussetzung Eingabe als String.
   */
  public Voraussetzung(String voraussetzung) {
    this.voraussetzung = voraussetzung;
  }

  public String getVoraussetzung() {
    return voraussetzung;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Voraussetzung that)) {
      return false;
    }
    return Objects.equals(voraussetzung, that.voraussetzung);
  }

  @Override
  public int hashCode() {
    return Objects.hash(voraussetzung);
  }
}
