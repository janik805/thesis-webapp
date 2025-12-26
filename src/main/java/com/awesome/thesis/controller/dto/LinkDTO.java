package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record LinkDTO (
        @NotBlank(message = "Bitte das Feld nicht leer lassen") @URL(message = "Bitte eine valide Url eingeben") String url,
        @NotBlank(message = "Bitte eine Beschreibung einfügen")String urlBeschreibung) {
}
