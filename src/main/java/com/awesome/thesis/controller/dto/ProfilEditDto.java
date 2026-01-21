package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.NotEmpty;

/**
 * Record zur Validierung des Names bei der Profilbearbeitung.
 *
 * @param name neuer Name des Betreuenden
 */
public record ProfilEditDto(@NotEmpty(message = "Name darf nicht leer sein") String name) {
}
