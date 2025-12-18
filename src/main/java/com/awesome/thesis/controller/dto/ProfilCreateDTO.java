package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProfilCreateDTO(@NotNull(message = "ID darf nicht leer sein") @Min(value = 1, message = "ID muss eine positive, ganze Zahl sein") Long id, @NotEmpty(message = "Name darf nicht leer sein") String name) {
}
