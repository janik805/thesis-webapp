package com.awesome.thesis.logic.domain.model.themen;

import com.awesome.thesis.annotations.AggregateRoot;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.annotation.Id;

/**
 * Das Thema Aggregat.
 */
@AggregateRoot
public class Thema {

  @Id
  private final Integer id;
  private String titel;
  private String beschreibung;
  private Set<ThemaLink> links;
  private final int profilId;
  private Set<ThemaVoraussetzung> voraussetzungen;
  private Set<ThemaFachgebiet> fachgebiete;
  private Set<ThemaDateiValue> dateien;

  /**
   * Erstellt ein Thema mit allen Parametern.
   *
   * @param id Der Schlüssel des Themas.
   * @param titel Der Titel des Themas.
   * @param beschreibung Die Beschreibung des Themas.
   * @param profilId Die Id des Profils, dem das Thema gehört.
   * @param links Die dem Thema zugeordneten Links.
   * @param voraussetzungen Die dem Thema zugeordneten Voraussetzungen.
   * @param fachgebiete Die dem Thema zugeordneten Fachgebiete.
   * @param dateien Die dem Thema zugeordneten Dateien.
   */
  public Thema(Integer id, String titel, String beschreibung, int profilId, Set<ThemaLink> links,
      Set<ThemaVoraussetzung> voraussetzungen, Set<ThemaFachgebiet> fachgebiete,
      Set<ThemaDateiValue> dateien) {
    this.id = id;
    this.titel = titel;
    this.beschreibung = beschreibung;
    this.links = new HashSet<>(links);
    this.profilId = profilId;
    this.voraussetzungen = new HashSet<>(voraussetzungen);
    this.fachgebiete = new HashSet<>(fachgebiete);
    this.dateien = new HashSet<>(dateien);
  }

  /**
   * Erstellt ein Thema mit nur den nötigen Parametern gesetzt.
   *
   * @param titel Der Titel des Themas.
   * @param profilId Die Id des Profils, dem das Thema gehört.
   */
  public Thema(String titel, int profilId) {
    this.id = null;
    this.titel = titel;
    this.profilId = profilId;
    this.beschreibung = "";
    this.links = new HashSet<>();
    this.voraussetzungen = new HashSet<>();
    this.fachgebiete = new HashSet<>();
    this.dateien = new HashSet<>();
  }

  public Set<ThemaFachgebiet> getFachgebiete() {
    return Set.copyOf(fachgebiete);
  }

  /**
   * Formatiert die Fachgebiete für themen.html.
   *
   * @return formatierter String von Fachgebieten.
   */
  public String fachgebieteString() {
    Set<String> stringFachgebiete = fachgebiete.stream().map(ThemaFachgebiet::fachgebiet)
        .collect(Collectors.toSet());
    if (fachgebiete.isEmpty()) {
      return "";
    }
    return String.join(", ", stringFachgebiete);
  }

  /**
   * Fügt ein Fachgebiet hinzu.
   *
   * @param fachgebiet Das Fachgebiet, das hinzugefügt werden soll.
   */
  public void addFachgebiet(ThemaFachgebiet fachgebiet) {
    fachgebiete.add(fachgebiet);
  }

  /**
   * Löscht ein Fachgebiet falls vorhanden.
   *
   * @param fachgebiet Das Fachgebiet, das gelöscht werden soll.
   */
  public void removeFachgebiet(ThemaFachgebiet fachgebiet) {
    fachgebiete.remove(fachgebiet);
  }

  /**
   * Gibt true zurück falls Fachgebiet vorhanden.
   *
   * @param fachgebiet Das Fachgebiet, das gesucht werden soll.
   */
  public boolean hasFachgebiet(ThemaFachgebiet fachgebiet) {
    return fachgebiete.contains(fachgebiet);
  }

  /**
   * Ersetzt alle Voraussetzungen durch das Eingegebene.
   *
   * @param voraussetzungen Set von Voraussetzungen, dass das aktuell Gespeicherte ersetzt
   */
  public void updateVoraussetzungen(Set<ThemaVoraussetzung> voraussetzungen) {
    this.voraussetzungen.clear();
    this.voraussetzungen.addAll(voraussetzungen);
  }

  /**
   * Löscht eine Voraussetzung falls vorhanden.
   *
   * @param voraussetzung Die Voraussetzung, die, falls vorhanden gelöscht wird.
   */
  public void removeVoraussetzung(ThemaVoraussetzung voraussetzung) {
    voraussetzungen.remove(voraussetzung);
  }

  public Set<ThemaVoraussetzung> getVoraussetzungen() {
    return Set.copyOf(voraussetzungen);
  }

  /**
   * Gibt true zurück falls Voraussetzung vorhanden.
   *
   * @param voraussetzung Die Voraussetzung, die gesucht werden soll.
   */
  public boolean hasVoraussetzung(ThemaVoraussetzung voraussetzung) {
    return voraussetzungen.contains(voraussetzung);
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

  /**
   * Gibt true zurück falls Beschreibung vorhanden.
   */
  public boolean hasBeschreibung() {
    return beschreibung != null && !beschreibung.isEmpty();
  }

  /**
   * Fügt einen Link zum Thema hinzu.
   *
   * @param link Der Eingabewert.
   */
  public void addUrl(ThemaLink link) {
    links.add(link);
  }

  public Set<ThemaLink> getLinks() {
    return Set.copyOf(links);
  }

  /**
   * Löscht den Link falls vorhanden.
   *
   * @param link Der Eingabewert.
   */
  public void removeUrl(ThemaLink link) {
    links.remove(link);
  }

  public Integer getId() {
    return id;
  }

  public int getProfilId() {
    return profilId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Thema thema = (Thema) o;
    return Objects.equals(id, thema.id) && Objects.equals(profilId, thema.profilId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  /**
   * Fügt Informationen zu einer Datei zum Thema hinzu.
   *
   * @param datei Der Eingabewert.
   */
  public void addDatei(ThemaDateiValue datei) {
    dateien.add(datei);
  }

  /**
   * Löscht Dateiinformationen falls vorhanden.
   *
   * @param datei Die Eingabe
   */
  public void removeDatei(ThemaDateiValue datei) {
    dateien.remove(datei);
  }

  public Set<ThemaDateiValue> getDateien() {
    return Set.copyOf(dateien);
  }

  /**
   * Guckt, ob das Thema alle Interessen und Voraussetzungen hat, welche eingegeben wurden.
   *
   * @param voraussetzungen Die Voraussetzungen
   * @param interessen Die Interessen
   * @return True, falls alles von der Eingabe enthalten, false sonst.
   */
  public boolean fitsRequirements(Set<ThemaVoraussetzung> voraussetzungen,
      Set<ThemaFachgebiet> interessen) {
    if ((interessen == null || interessen.isEmpty()) && (voraussetzungen == null
        || voraussetzungen.isEmpty())) {
      return true;
    }
    if (voraussetzungen == null || voraussetzungen.isEmpty()) {
      return this.fachgebiete.containsAll(interessen);
    }
    if (interessen == null || interessen.isEmpty()) {
      return this.voraussetzungen.containsAll(voraussetzungen);
    }
    return this.voraussetzungen.containsAll(voraussetzungen) && this.fachgebiete.containsAll(
        interessen);
  }

  /**
   * Berechnet den Rang, wie viele Voraussetzungen und Interessen von
   * der Eingabe in dem Thema vorhanden sind.
   *
   * @param voraussetzungen Die Voraussetzungen
   * @param interessen Die Interessen
   * @return Anzahl der Interessen/Voraussetzungen vom Thema, die mit der Eingabe übereinstimmen.
   */
  public long calcRang(Set<ThemaVoraussetzung> voraussetzungen, Set<ThemaFachgebiet> interessen) {
    if ((interessen == null || interessen.isEmpty()) && (voraussetzungen == null
        || voraussetzungen.isEmpty())) {
      return 0;
    }
    if (voraussetzungen == null || voraussetzungen.isEmpty()) {
      boolean hasNoVoraussetzungen = this.voraussetzungen.isEmpty();
      if (hasNoVoraussetzungen) {
        return 0;
      }
    }

    if (!voraussetzungen.containsAll(this.voraussetzungen)) {
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
