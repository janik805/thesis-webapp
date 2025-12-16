package com.awesome.thesis.logic.domain.model.themen;

import com.awesome.thesis.annotations.AggregateRoot;
import com.awesome.thesis.logic.domain.model.links.Link;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AggregateRoot
public class Thema {
    private String id;
    private String titel;
    private String beschreibung;
    private final List<Link> links;

    public Thema(String titel) {
        this.titel = titel;
        this.links = new ArrayList<>();
    }

    public void setTitel(String titel) { this.titel = titel; }

    public String getTitel() {
        return titel;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung=beschreibung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public boolean hasBeschreibung() {
        return !beschreibung.isEmpty();
    }

    public void addUrl(Link link) {
        links.add(link);
    }

    public List<Link> getLinks() {
        return links;
    }

    public void removeUrl(Link link) {
        links.remove(link);
    }

    public boolean hasLinks() {
        return !links.isEmpty();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thema thema = (Thema) o;
        return Objects.equals(id, thema.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
