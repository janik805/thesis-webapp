package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateRoot;
import com.awesome.thesis.logic.domain.model.links.Link;

import java.util.HashSet;
import java.util.Set;

@AggregateRoot
public class Profil {
    private final long id;
    private String name;
    private final Set<Kontakt> kontakte;
    private final Set<String> fachgebiete;
    private final Set<Link> links;

    public Profil(long id, String name) {
        this.id = id;
        this.name = name;
        this.kontakte = new HashSet<>();
        this.fachgebiete = new HashSet<>();
        this.links = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Set<Kontakt> getKontakte() {
        return kontakte;
    }

    public void addKontakt(Kontakt kontakt) {
        kontakte.add(kontakt);
    }

    public void removeKontakt(Kontakt kontakt) {
        kontakte.remove(kontakt);
    }

    public void addEmail(String label, String wert) {
        Kontakt kontakt = new Kontakt(label, wert, Kontaktart.EMAIL);
        kontakte.add(kontakt);
    }

    public void addTel(String label, String wert) {
        Kontakt kontakt = new Kontakt(label, wert, Kontaktart.TEL);
        kontakte.add(kontakt);
    }

    public Set<String> getFachgebiete() {
        return fachgebiete;
    }

    public String fachgebieteString() {
        if (fachgebiete.isEmpty()) {
            return "";
        }
        return String.join(", ", fachgebiete);
    }

    public void addFachgebiet(String fachgebiet) {
        fachgebiete.add(fachgebiet);
    }

    public boolean hasFachgebiet(String fachgebiet) {
        return fachgebiete.contains(fachgebiet);
    }

    public void removeFachgebiet(String fachgebiet) {
        fachgebiete.remove(fachgebiet);
    }

    public boolean fitsInterests(Set<String> interessen) {
        return fachgebiete.containsAll(interessen);
    }

    public Set<Link> getLinks() {
        return links;
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public void removeLink(Link link) {
        links.remove(link);
    }
}
