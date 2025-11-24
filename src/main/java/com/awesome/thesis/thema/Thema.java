package com.awesome.thesis.themen.thema;

import com.awesome.thesis.themen.Link;
import java.util.ArrayList;
import java.util.List;

public class Thema {
    private String titel;
    private String beschreibung;
    private final List<Link> urls = new ArrayList<>();

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
        urls.add(link);
    }

    public List<Link> getUrls() {
        return urls;
    }

    public void removeUrl(Link link) {
        urls.remove(link);
    }
}
