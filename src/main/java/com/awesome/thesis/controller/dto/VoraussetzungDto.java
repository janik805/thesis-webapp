package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Das Dto zwischen dem Controller und der Voraussetzung Aggregat.
 *
 * @param voraussetzung Die eingegebene Voraussetzung.
 */
public record VoraussetzungDto(
        @NotBlank(message = "Bitte kein leeres Feld hinzufügen") String voraussetzung) {
}
