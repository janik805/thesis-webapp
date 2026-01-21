package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Das Dto zwischen dem Controller und dem Link-Aggregat.
 *
 * @param url             Die Url.
 * @param urlBeschreibung Die Beschreibung der Url.
 */
public record LinkDto(
    @NotBlank(message = "Bitte das Feld nicht leer lassen")
    @URL(message = "Bitte eine valide Url eingeben") String url,
    @NotBlank(message = "Bitte eine Beschreibung einfügen") String urlBeschreibung) {
}
