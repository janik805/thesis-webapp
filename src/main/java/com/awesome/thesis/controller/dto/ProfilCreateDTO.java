package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record ProfilCreateDTO(@NotEmpty String id, String name) {
}
