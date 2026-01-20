package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateValue;

/**
 * Dieser Record dient der fachlichen Speicherung von Kontakten.
 *
 * @param label      Beschreibung von Kontakt
 * @param wert       Speicherung von Kontakt
 * @param kontaktart Speicherung von Kontaktart {@link ProfilKontaktart}
 */
@AggregateValue
public record ProfilKontakt(String label, String wert, ProfilKontaktart kontaktart) {
  /**
   * Konstruktor für Erstellung, wenn label leer ist, wird es auf das Standardlabel
   * von {@link ProfilKontaktart} gesetzt.
   *
   * @param label      Beschreibung von Kontakt
   * @param wert       Speicherung von Kontakt
   * @param kontaktart Speicherung von Kontaktart {@link ProfilKontaktart}
   */
  public ProfilKontakt {
    if (label.isEmpty()) {
      label = kontaktart.getLabel();
    }
  }
  
  public String getHref() {
    return String.format(kontaktart.getHref(), wert);
  }
}
