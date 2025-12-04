package com.awesome.thesis.logic.domain.model.themen;

import com.awesome.thesis.logic.domain.model.links.Link;
import java.util.ArrayList;
import java.util.List;

public class Thema {
    private String titel;
    private String beschreibung;
    private final List<Link> links = new ArrayList<>();

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
}
