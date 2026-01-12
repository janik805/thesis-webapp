package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record ProfilEditDTO(@NotEmpty(message="Name darf nicht leer sein") String name) {
}
