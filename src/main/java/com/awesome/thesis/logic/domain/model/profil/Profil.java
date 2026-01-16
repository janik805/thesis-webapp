package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateRoot;
import org.springframework.data.annotation.Id;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AggregateRoot
public class Profil {
    @Id
    private final int id;
    private String name;
    private Set<Kontakt> kontakte;
    private Set<ProfilFachgebiet> fachgebiete;
    private Set<ProfilLink> links;
    private Set<ThemaValue> themen;
    private Set<DateiValue> dateien;

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

    public int getId() {
        return id;
    }

    public Set<Kontakt> getKontakte() {
        return kontakte;
    }

    public void setKontakte(Set<Kontakt> kontakte) {
        this.kontakte = kontakte;
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
        return fachgebiete.stream().map(ProfilFachgebiet::fachgebiet).collect(Collectors.toSet());
    }

    public void setFachgebiete(Set<String> fachgebiete) {
        this.fachgebiete = fachgebiete.stream().map(ProfilFachgebiet::new).collect(Collectors.toSet());
    }

    public String fachgebieteString() {
        if (fachgebiete.isEmpty()) {
            return "";
        }
        return String.join(", ", getFachgebiete());
    }

    public void addFachgebiet(String fachgebiet) {
        fachgebiete.add(new ProfilFachgebiet(fachgebiet));
    }

    public boolean hasFachgebiet(String fachgebiet) {
        return getFachgebiete().contains(fachgebiet);
    }

    public void removeFachgebiet(String fachgebiet) {
        fachgebiete.remove(new ProfilFachgebiet(fachgebiet));
    }

    public boolean fitsInterests(Set<String> interessen) {
        return getFachgebiete().containsAll(interessen);
    }

    public long compRank(Set<String> interessen) {
        return interessen.stream()
                .filter(getFachgebiete()::contains)
                .count();
    }

    public Set<ProfilLink> getLinks() {
        return links;
    }

    public void setLinks(Set<ProfilLink> links) {
        this.links = links;
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

    public void setThemen(Set<ThemaValue> themen) {
        this.themen = themen;
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

    public void setDateien(Set<DateiValue> dateien) {
        this.dateien = dateien;
    }
}
