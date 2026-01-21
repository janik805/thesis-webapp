package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record zur Validierung von einem neuen Fachgebiet.
 *
 * @param fachgebiet Name vom Fachgebiet
 */
public record FachgebietDto(@NotBlank(message = "Fachgebiet kann nicht leer sein")
                            String fachgebiet) {
}
