package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateValue;

@AggregateValue
public record ThemaValue (String id, String name) {
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ThemaValue other)) {
            return false;
        }
        return this.id.equals(other.id);
    }
}
