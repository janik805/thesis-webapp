package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateValue;

/**
 * Dieser Record dient der fachlichen Speicherung von Fachgebieten.
 *
 * @param fachgebiet Name des Fachgebietes
 */
@AggregateValue
public record ProfilFachgebiet(String fachgebiet) {
  @Override
  public String toString() {
    return fachgebiet;
  }
}
