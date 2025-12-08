package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateRoot;
import com.awesome.thesis.annotations.AggregateEntity;
import com.awesome.thesis.annotations.AggregateValue;

import java.util.ArrayList;
import java.util.List;

@AggregateRoot
public class Profil {
    private String id;
    private String name;
    private final List<Kontakt> kontakte;

    public Profil(String name) {
        this.name = name;
        this.kontakte = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Kontakt> getKontakte() {
        return kontakte;
    }

    public void addKontakt(Kontakt kontakt) {
        kontakte.add(kontakt);
    }

    public void removeKontakt(Kontakt kontakt) {
        kontakte.remove(kontakt);
    }
}
