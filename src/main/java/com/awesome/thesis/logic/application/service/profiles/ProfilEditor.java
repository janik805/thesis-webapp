package com.awesome.thesis.logic.application.service.profiles;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.profil.ProfilDateiValue;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontakt;
import com.awesome.thesis.logic.domain.model.profil.ProfilLink;
import com.awesome.thesis.logic.domain.model.profil.ProfilThemaValue;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * Diese Klasse ist der Service von {@link Profil}.
 */
@Service
public class ProfilEditor {
  @SuppressFBWarnings(value = "EI_EXPOSE_REP",
      justification = "Spring Konstruktor Injection")
  private final ProfileRepoI profile;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP",
      justification = "Spring Konstruktor Injection")
  private final FachgebieteEditor fachgebieteEditor;
  
  public ProfilEditor(ProfileRepoI profile, FachgebieteEditor fachgebieteEditor) {
    this.profile = profile;
    this.fachgebieteEditor = fachgebieteEditor;
  }
  
  public List<Profil> getAll() {
    return profile.getAll();
  }
  
  /**
   * Diese Methode lädt Profil aus der Datenbank.
   *
   * @param id Profil-Id
   * @return gibt das profil mit der übergebenen Id zurück
   */
  public Profil get(int id) {
    if (profile.containsKey(id)) {
      return profile.get(id);
    }
    throw new IllegalArgumentException("Es existiert kein Profil mit der Id " + id);
  }
  
  /**
   * Diese Methode wählt alle Profile zurück, die zu den Interessen passen.
   *
   * @param interessen Set von Interessen
   * @return gibt Liste von passenden Profilen zurück
   */
  public List<Profil> getFitting(Set<String> interessen) {
    if (interessen == null || interessen.isEmpty()) {
      return getAll();
    }
    return profile.getAll().stream()
        .filter(p -> p.fitsInterests(interessen))
        .toList();
  }
  
  /**
   * Diese Methode rankt Profile nach den Interessen.
   *
   * @param interessen Set von Interessen
   * @return gibt sortierte Liste der Profile zurück
   */
  public List<Profil> getMatching(Set<String> interessen) {
    if (interessen == null || interessen.isEmpty()) {
      return getAll();
    }
    return profile.getAll().stream()
        .sorted((profil1, profil2) ->
            Long.compare(
                profil2.compRank(interessen),
                profil1.compRank(interessen)
            ))
        .toList();
  }
  
  public boolean contains(int id) {
    return profile.containsKey(id);
  }
  
  /**
   * Diese Methode dient der Erstellung von Betreuendenprofilen.
   *
   * @param id   Github-Id
   * @param name Name des Betreuenden
   */
  public void create(int id, String name) {
    if (!profile.containsKey(id)) {
      Profil profil = new Profil(id, name);
      profile.save(profil);
    }
  }
  
  public void delete(int id) {
    profile.delete(id);
  }
  
  /**
   * Methode um Namen eines Profils zu ändern.
   *
   * @param id   Id des Profils
   * @param name neuer Name des Betreuenden
   */
  public void editName(int id, String name) {
    Profil profil = get(id);
    profil.setName(name);
    profile.save(profil);
  }
  
  /**
   * Diese Methode fügt eine E-Mail hinzu.
   *
   * @param id    Id des Profils
   * @param label Beschreibung der E-Mail
   * @param wert  E-Mail Adresse
   */
  public void addEmail(int id, String label, String wert) {
    Profil profil = get(id);
    profil.addEmail(label, wert);
    profile.save(profil);
  }
  
  /**
   * Diese Methode fügt eine Telefonnummer hinzu.
   *
   * @param id    Id des Profils
   * @param label Beschreibung der Telefonnummer
   * @param wert  Telefonnummer
   */
  public void addTel(int id, String label, String wert) {
    Profil profil = get(id);
    profil.addTel(label, wert);
    profile.save(profil);
  }
  
  /**
   * Methode um Kontakt zu entfernen.
   *
   * @param id            Id des Profils
   * @param profilKontakt {@link ProfilKontakt}
   */
  public void removeKontakt(int id, ProfilKontakt profilKontakt) {
    Profil profil = get(id);
    profil.removeKontakt(profilKontakt);
    profile.save(profil);
  }
  
  /**
   * Methode um Fachgebiet hinzuzufügen.
   *
   * @param id         Id des Profils
   * @param fachgebiet String mit Name des Fachgebietes
   */
  public void addFachgebiet(int id, String fachgebiet) {
    Profil profil = get(id);
    fachgebieteEditor.add(fachgebiet);
    profil.addFachgebiet(fachgebiet);
    profile.save(profil);
  }
  
  /**
   * Methode um Fachgebiet zu entfernen.
   *
   * @param id         Id des Profils
   * @param fachgebiet String mit Namen des Fachgebietes
   */
  public void removeFachgebiet(int id, String fachgebiet) {
    Profil profil = get(id);
    profil.removeFachgebiet(fachgebiet);
    profile.save(profil);
    fachgebieteEditor.remove(fachgebiet);
  }
  
  /**
   * Methode um Link hinzuzufügen.
   *
   * @param id              Id des Profils
   * @param url             Url des Links
   * @param urlBeschreibung Beschreibung des Links
   */
  public void addLink(int id, String url, String urlBeschreibung) {
    Profil profil = get(id);
    ProfilLink link = new ProfilLink(url, urlBeschreibung);
    profil.addLink(link);
    profile.save(profil);
  }
  
  /**
   * Methode um Link zu löschen.
   *
   * @param id   Id des Profils
   * @param link {@link ProfilLink}
   */
  public void removeLink(int id, ProfilLink link) {
    Profil profil = get(id);
    profil.removeLink(link);
    profile.save(profil);
  }
  
  /**
   * Methode um Thema hinzufügen.
   *
   * @param id      Id des Profils
   * @param themaId Id des Themas
   * @param name    Name des Themas
   */
  public void addThema(int id, Integer themaId, String name) {
    Profil profil = get(id);
    profil.addThema(new ProfilThemaValue(themaId, name));
    profile.save(profil);
  }
  
  /**
   * Methode um Thema zu entfernen.
   *
   * @param id      Id des Profils
   * @param themaId Id des Themas
   */
  public void removeThema(int id, Integer themaId) {
    Profil profil = get(id);
    profil.removeThema(new ProfilThemaValue(themaId, ""));
    profile.save(profil);
  }
  
  /**
   * Method um Datei hinzuzufügen.
   *
   * @param id           Id des Profils
   * @param dateiId      Id der Datei
   * @param name         Name der Datei
   * @param beschreibung Beschreibung der Datei
   */
  public void addDatei(int id, String dateiId, String name, String beschreibung) {
    Profil profil = get(id);
    profil.addDatei(new ProfilDateiValue(dateiId, name, beschreibung));
    profile.save(profil);
  }
  
  /**
   * Method um Datei zu entfernen.
   *
   * @param id      Id des Profils
   * @param dateiId Id der Datei
   */
  public void removeDatei(int id, String dateiId) {
    Profil profil = get(id);
    profil.removeDatei(new ProfilDateiValue(dateiId, "", ""));
    profile.save(profil);
  }
}
