package com.awesome.thesis.controller.dto.kontakt;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Record zur Erstellung von einem neuen E-Mail Kontakt.
 *
 * @param type Kontakttyp
 * @param label Beschreibung von der E-Mail
 * @param wert E-Mail Adresse
 */
public record EmailKontaktDto(String type, String label,
                              @NotBlank(message = "Email darf nicht leer sein")
                              @Email(message = "Bitte geben Sie eine gültige Email-Adresse ein")
                              String wert) {
}