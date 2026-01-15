package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateRoot;

import java.util.HashSet;
import java.util.Set;

@AggregateRoot
public class Profil {
    private final int id;
    private String name;
    private final Set<Kontakt> kontakte;
    private final Set<String> fachgebiete;
    private final Set<ProfilLink> links;
    private final Set<ThemaValue> themen;
    private final Set<DateiValue> dateien;

    public Profil(int id, String name) {
        this.id = id;
        this.name = name;
        this.kontakte = new HashSet<>();
        this.fachgebiete = new HashSet<>();
        this.links = new HashSet<>();
        this.themen = new HashSet<>();
        this.dateien = new HashSet<>();
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

    public long compRank(Set<String> interessen) {
        return interessen.stream()
                .filter(fachgebiete::contains)
                .count();
    }

    public Set<ProfilLink> getLinks() {
        return links;
    }

    public void addLink(ProfilLink link) {
        links.add(link);
    }

    public void removeLink(ProfilLink link) {
        links.remove(link);
    }

    public Set<ThemaValue> getThemen() {
        return themen;
    }

    public void addThema(ThemaValue thema) {
        themen.remove(thema);
        themen.add(thema);
    }

    public void removeThema(ThemaValue thema) {
        themen.remove(thema);
    }

    public void addDatei(DateiValue datei) {
        dateien.remove(datei);
        dateien.add(datei);
    }

    public void removeDatei(DateiValue datei) {
        dateien.remove(datei);
    }

    public Set<DateiValue> getDateien() {
        return dateien;
    }
}
