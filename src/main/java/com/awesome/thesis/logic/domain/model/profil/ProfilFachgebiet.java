package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateValue;

@AggregateValue
public record ProfilFachgebiet(String fachgebiet) {
    @Override
    public String toString() {
        return fachgebiet;
    }
}
