package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateValue;

@AggregateValue
public enum Kontaktart {
    EMAIL("Email", "mailto:%s"),
    TEL("Telefon", "tel:%s");

    private final String label;
    private final String href;

    Kontaktart(String label, String href) {
        this.label = label;
        this.href = href;
    }

    public String getLabel() {
        return label;
    }

    public String getHref() {
        return href;
    }
}
