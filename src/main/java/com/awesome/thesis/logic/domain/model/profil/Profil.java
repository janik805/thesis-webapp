package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateRoot;
import org.springframework.data.annotation.Id;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AggregateRoot
public class Profil {
    @Id
    private final int id;
    private String name;
    private Set<ProfilKontakt> kontakte;
    private Set<ProfilFachgebiet> fachgebiete;
    private Set<ProfilLink> links;
    private Set<ProfilThemaValue> themen;
    private Set<ProfilDateiValue> dateien;

    public Profil(int id, String name) {
        this.id = id;
        this.name = name;
        this.kontakte = new HashSet<>();
        this.fachgebiete = new HashSet<>();
        this.links = new HashSet<>();
        this.themen = new HashSet<>();
        this.dateien = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProfilKontakt> getKontakte() {
        return kontakte;
    }

    public void setKontakte(Set<ProfilKontakt> kontakte) {
        this.kontakte = kontakte;
    }

    public void removeKontakt(ProfilKontakt profilKontakt) {
        kontakte.remove(profilKontakt);
    }

    public void addEmail(String label, String wert) {
        ProfilKontakt profilKontakt = new ProfilKontakt(label, wert, ProfilKontaktart.EMAIL);
        kontakte.add(profilKontakt);
    }

    public void addTel(String label, String wert) {
        ProfilKontakt profilKontakt = new ProfilKontakt(label, wert, ProfilKontaktart.TEL);
        kontakte.add(profilKontakt);
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

    public Set<ProfilThemaValue> getThemen() {
        return themen;
    }

    public void setThemen(Set<ProfilThemaValue> themen) {
        this.themen = themen;
    }

    public void addThema(ProfilThemaValue thema) {
        removeThema(thema);
        themen.add(thema);
    }

    public void removeThema(ProfilThemaValue thema) {
        themen.remove(thema);
    }

    public void addDatei(ProfilDateiValue datei) {
        removeDatei(datei);
        dateien.add(datei);
    }

    public void removeDatei(ProfilDateiValue datei) {
        dateien.remove(datei);
    }

    public Set<ProfilDateiValue> getDateien() {
        return dateien;
    }

    public void setDateien(Set<ProfilDateiValue> dateien) {
        this.dateien = dateien;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Profil profil = (Profil) o;
        return id == profil.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
