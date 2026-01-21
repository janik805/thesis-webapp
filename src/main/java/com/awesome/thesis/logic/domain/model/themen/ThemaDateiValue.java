package com.awesome.thesis.logic.domain.model.themen;

import com.awesome.thesis.annotations.AggregateValue;

/**
 * Repräsentiert eine Datei innerhalb des Thema-Aggregats.
 *
 * @param id Der identifizierende Schlüssel der Datei.
 * @param name Der Name der Datei.
 * @param beschreibung Die Beschreibung der Datei.
 */
@AggregateValue
public record ThemaDateiValue(String id, String name, String beschreibung) {

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof com.awesome.thesis.logic.domain.model.themen.ThemaDateiValue other)) {
      return false;
    }
    return this.id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
