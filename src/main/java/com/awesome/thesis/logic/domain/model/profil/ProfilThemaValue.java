package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateValue;

/**
 * Dieser Record dient der fachlichen Speicherung von einem Snapshot eines Themas.
 *
 * @param id Schlüssel von Thema
 * @param name Name des Themas
 */
@AggregateValue
public record ProfilThemaValue(Integer id, String name) {
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof ProfilThemaValue other)) {
      return false;
    }
    return this.id.equals(other.id);
  }
  
  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
