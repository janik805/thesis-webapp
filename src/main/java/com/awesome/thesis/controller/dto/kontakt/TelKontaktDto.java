package com.awesome.thesis.controller.dto.kontakt;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Record zur Erstellung von einem neuen Telefonkontakt.
 *
 * @param type Kontakttyp
 * @param label Beschreibung von dem Kontakt
 * @param wert Telefonnummer
 */
public record TelKontaktDto(String type, String label,
                            @NotBlank(message = "Telefon-Nummer darf nicht leer sein")
                          @Pattern(message = "Bitte geben Sie eine gültige Telefon-Nummer ein",
                                  regexp = "^(?:0[1-9][0-9 ()\\-\\/]{5,}|\\+49[1-9][0-9 "
                                      + "()\\-\\/]{5,}|\\+[1-9][0-9 ()\\-\\/]{5,})$")
                            String wert){
}