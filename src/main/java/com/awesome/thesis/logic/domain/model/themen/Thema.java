package com.awesome.thesis.logic.domain.model.themen;
import com.awesome.thesis.annotations.AggregateRoot;
import java.util.*;
import java.util.stream.Collectors;

@AggregateRoot
public class Thema {
    private Integer id;
    private String titel;
    private String beschreibung;
    private final Set<ThemaLink> links;
    private final int profilID;
    private final Set<ThemaVoraussetzung> voraussetzungen;
    private final Set<ThemaFachgebiet> fachgebiete;
    private final Set<ThemaDateiValue> dateien;

    public Thema(String titel, int id) {
        this.titel = titel;
        this.profilID = id;
        this.links = new HashSet<>();
        this.voraussetzungen = new HashSet<>();
        this.fachgebiete = new HashSet<>();
        this.dateien = new HashSet<>();
    }

    public Set<ThemaFachgebiet> getFachgebiete() {
        return fachgebiete;
    }

    public String fachgebieteString() {
        Set<String> stringFachgebiete = fachgebiete.stream().map(e -> e.fachgebiet()).collect(Collectors.toSet());
        if (fachgebiete.isEmpty()) {
            return "";
        }
        return String.join(", ", stringFachgebiete);
    }

    public void addFachgebiet(ThemaFachgebiet fachgebiet) {
        fachgebiete.add(fachgebiet);
    }

    public void removeFachgebiet(ThemaFachgebiet fachgebiet) {
        fachgebiete.remove(fachgebiet);
    }

    public boolean hasFachgebiet(ThemaFachgebiet fachgebiet) {
        return fachgebiete.contains(fachgebiet);
    }

    public void updateVoraussetzungen(Set<ThemaVoraussetzung> voraussetzungen) {
        this.voraussetzungen.clear();
        this.voraussetzungen.addAll(voraussetzungen);
    }

    public void removeVoraussetzung(ThemaVoraussetzung voraussetzung) {
        voraussetzungen.remove(voraussetzung);
    }

    public Set<ThemaVoraussetzung> getVoraussetzungen() {
        return voraussetzungen;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getTitel() {
        return titel;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void addDatei(ThemaDateiValue datei) {
        dateien.add(datei);
    }

    public void removeDatei(ThemaDateiValue datei) {
        dateien.remove(datei);
    }

    public Set<ThemaDateiValue> getDateien() {
        return dateien;
    }

    public boolean fitsRequirements(Set<ThemaVoraussetzung> voraussetzungen, Set<ThemaFachgebiet> interessen) {
        if ((interessen == null || interessen.isEmpty()) && (voraussetzungen == null || voraussetzungen.isEmpty())) {
            return true;
        }
        if (voraussetzungen == null || voraussetzungen.isEmpty()) {
            return this.fachgebiete.containsAll(interessen);
        }
        if (interessen == null || interessen.isEmpty()) {
            return this.voraussetzungen.containsAll(voraussetzungen);
        }
        return this.voraussetzungen.containsAll(voraussetzungen) && this.fachgebiete.containsAll(interessen);
    }

    public long calcRang(Set<ThemaVoraussetzung> voraussetzungen, Set<ThemaFachgebiet> interessen) {
        if ((interessen == null || interessen.isEmpty()) && (voraussetzungen == null || voraussetzungen.isEmpty())) {
            return 0;
        }
        if (voraussetzungen == null || voraussetzungen.isEmpty()) {
            boolean hasNoVoraussetzungen = this.voraussetzungen.isEmpty();
            if(hasNoVoraussetzungen) {
                return 0;
            }
        }

        if(!voraussetzungen.containsAll(this.voraussetzungen)) {
            return -1;
        }

        long voraussetzungenAnzahl = voraussetzungen.stream()
                .filter(this.voraussetzungen::contains)
                .count();

        if (interessen == null || interessen.isEmpty()) {
            return voraussetzungenAnzahl;
        }

        long matchAnzahl = 0;

        for (ThemaFachgebiet fachgebiet : this.fachgebiete) {
            if (interessen.contains(fachgebiet)) {
                matchAnzahl++;
            }
        }

        return matchAnzahl;
    }
}
