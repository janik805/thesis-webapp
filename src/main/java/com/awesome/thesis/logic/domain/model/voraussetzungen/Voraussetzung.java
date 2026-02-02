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
  private final Integer version;

  /**
   * Initialisiert die Voraussetzung.
   *
   * @param voraussetzung Eingabe als String.
   */
  public Voraussetzung(String voraussetzung) {
    this.voraussetzung = voraussetzung;
    this.version = null;
  }

  public Voraussetzung(String voraussetzung, Integer version) {
    this.voraussetzung = voraussetzung;
    this.version = version;
  }

  public String voraussetzung() {
    return voraussetzung;
  }

  public Integer version() {
    return version;
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
