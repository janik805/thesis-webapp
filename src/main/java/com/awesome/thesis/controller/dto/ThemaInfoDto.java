package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Dto für Thema mit dem Titel und der Beschreibung des Themas.
 *
 * @param titel        Der Titel des Themas.
 * @param beschreibung Die Beschreibung des Themas.
 */
public record ThemaInfoDto(
    @NotBlank(message = "Der Titel darf nicht leer gelassen werden") String titel,
    String beschreibung) {

}
