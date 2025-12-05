package com.awesome.thesis.logic.domain.model.themen;

import com.awesome.thesis.logic.domain.model.links.Link;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Thema {
    private String titel;
    private String beschreibung;
    private final List<Link> links;

    public Thema(String titel, String beschreibung) {
        this.titel = titel;
        this.beschreibung = beschreibung;
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

    public void addUrl(Link link) {
        links.add(link);
    }

    public List<Link> getLinks() {
        return links;
    }

    public void removeUrl(Link link) {
        links.remove(link);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thema thema = (Thema) o;
        return Objects.equals(titel, thema.titel)
                && Objects.equals(beschreibung, thema.beschreibung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titel, beschreibung);
    }

}
