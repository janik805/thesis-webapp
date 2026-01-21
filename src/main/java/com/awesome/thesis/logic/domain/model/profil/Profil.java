package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateRoot;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.annotation.Id;

/**
 * Die Klasse Profil ist das Aggregat Root des Aggregates profil und
 * speichert die Betreuendenprofile.
 */
@AggregateRoot
public class Profil {
  @Id
  private final int id;
  private String name;
  private final Set<ProfilKontakt> kontakte;
  private Set<ProfilFachgebiet> fachgebiete;
  private Set<ProfilLink> links;
  private Set<ProfilThemaValue> themen;
  private final Set<ProfilDateiValue> dateien;
  
  /**
   * Der Konstruktor erstellt eines neuen Betreuendenprofils.
   *
   * @param id Speichert die Github-Id als natürlichen Schlüssel
   * @param name Namen des Betreuenden
   */
  public Profil(int id, String name) {
    this.id = id;
    this.name = name;
    this.kontakte = new HashSet<>();
    this.fachgebiete = new HashSet<>();
    this.links = new HashSet<>();
    this.themen = new HashSet<>();
    this.dateien = new HashSet<>();
  }
  
  /**
   * Konstruktor zum Laden eines existierenden Betreuendenprofils.
   * Alle lokalen Felder werden mit den Parametern initialisiert.
   *
   * @param id Github-Id eines Betreuenden
   * @param name Name eines Betreuenden
   * @param kontakte Speichert ein Set von {@link ProfilKontakt}
   * @param fachgebiete speichert ein Set von {@link ProfilFachgebiet}
   * @param links speichert ein Set von {@link ProfilLink}
   * @param themen speichert ein Set von {@link ProfilThemaValue}
   * @param dateien speichert ein Set von {@link ProfilDateiValue}
  */
  public Profil(int id, String name, Set<ProfilKontakt> kontakte,
                Set<ProfilFachgebiet> fachgebiete, Set<ProfilLink> links,
                Set<ProfilThemaValue> themen, Set<ProfilDateiValue> dateien) {
    this.id = id;
    this.name = name;
    this.kontakte = kontakte;
    this.fachgebiete = fachgebiete;
    this.links = links;
    this.themen = themen;
    this.dateien = dateien;
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
  
  /**
   * Fügt Fachgebiete als String zusammen für einfache Ausgabe.
   *
   * @return alle Fachgebiete zusammen als String
  */
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
  
  /**
   * Berechnet einen Rank Wert für den Vergleich, für den die gemeinsamen Interessen und Fachgebiete
   * gezählt werden.
   *
   * @param interessen Interessen für den Vergleich
   * @return gibt die Anzahl der Interessen die mit Fachgebieten übereinstimmen zurück
  */
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
  
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Profil profil = (Profil) o;
    return id == profil.id;
  }
  
  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
