package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Record zu Erstellung eines neuen Betreuendenprofils.
 *
 * @param id Github-Ids des Profils
 * @param name Name des Betreuenden
 */
public record ProfilCreateDto(@NotNull(message = "ID darf nicht leer sein")
                              @Min(value = 1, message = "ID muss eine positive, ganze Zahl sein")
                              Integer id,
                              @NotEmpty(message = "Name darf nicht leer sein") String name) {
}
