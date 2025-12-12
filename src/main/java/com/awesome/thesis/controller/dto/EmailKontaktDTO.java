package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailKontaktDTO (String label,
    @NotBlank(message = "Email darf nicht leer sein")
    @Email(message = "Bitte geben Sie eine gültige Email Adresse ein") String wert) {}
