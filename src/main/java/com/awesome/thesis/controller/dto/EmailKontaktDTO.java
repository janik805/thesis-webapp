package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailKontaktDTO(String label, @NotNull @Email String wert) {
}
