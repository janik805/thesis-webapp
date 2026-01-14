package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateValue;

@AggregateValue
public record DateiValue (String id, String name, String beschreibung ) {
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DateiValue other)) {
            return false;
        }
        return this.id.equals(other.id);
    }
}
