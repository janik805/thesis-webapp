package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateValue;

@AggregateValue
public record ProfilThemaValue(Integer id, String name) {
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProfilThemaValue other)) {
            return false;
        }
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
