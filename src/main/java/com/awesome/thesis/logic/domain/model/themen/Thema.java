package com.awesome.thesis.logic.domain.model.themen;

import com.awesome.thesis.annotations.AggregateRoot;
import com.awesome.thesis.logic.application.dto.DateiDTO;

import java.util.*;

@AggregateRoot
public class Thema {
    private String id;
    private String titel;
    private String beschreibung;
    private final Set<ThemaLink> links;
    private final int profilID;
    private final Set<Voraussetzung> voraussetzungen;
    private final Set<String> fachgebiete;
    private final Set<DateiDTO> dateien;

    public Thema(String titel, int id) {
        this.titel = titel;
        this.profilID = id;
        this.links = new HashSet<>();
        this.voraussetzungen = new HashSet<>();
        this.fachgebiete = new HashSet<>();
        this.dateien = new HashSet<>();
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

    public void removeFachgebiet(String fachgebiet) {
        fachgebiete.remove(fachgebiet);
    }

    public boolean hasFachgebiet(String fachgebiet) {
        return fachgebiete.contains(fachgebiet);
    }

    public void updateVoraussetzungen(Set<Voraussetzung> voraussetzungen) {
        this.voraussetzungen.clear();
        this.voraussetzungen.addAll(voraussetzungen);
    }

    public void removeVoraussetzung(Voraussetzung voraussetzung) {
        voraussetzungen.remove(voraussetzung);
    }

    public Set<Voraussetzung> getVoraussetzungen() {
        return voraussetzungen;
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

    public void addUrl(ThemaLink link) {
        links.add(link);
    }

    public Set<ThemaLink> getLinks() {
        return links;
    }

    public void removeUrl(ThemaLink link) {
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

    public int getProfilID() {
        return profilID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thema thema = (Thema) o;
        return Objects.equals(id, thema.id) && Objects.equals(profilID, thema.profilID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addDatei(DateiDTO datei) {
        dateien.add(datei);
    }

    public void removeDatei(DateiDTO datei) {
        dateien.remove(datei);
    }

    public Set<DateiDTO> getDateien() {
        return dateien;
    }

    public boolean fitsRequirements(Set<Voraussetzung> voraussetzungen, Set<String> interessen) {
        if((interessen == null || interessen.isEmpty()) && (voraussetzungen == null || voraussetzungen.isEmpty())) {
            return true;
        }
        if(voraussetzungen == null || voraussetzungen.isEmpty()) {
            return this.fachgebiete.containsAll(interessen);
        }
        if(interessen == null || interessen.isEmpty()) {
            return this.voraussetzungen.containsAll(voraussetzungen);
        }
         return this.voraussetzungen.containsAll(voraussetzungen) && this.fachgebiete.containsAll(interessen);
    }
}
