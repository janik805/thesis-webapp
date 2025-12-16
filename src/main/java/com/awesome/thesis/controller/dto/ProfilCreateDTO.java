package com.awesome.thesis.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record ProfilCreateDTO(@Min(1) String id, @NotEmpty String name) {
}
