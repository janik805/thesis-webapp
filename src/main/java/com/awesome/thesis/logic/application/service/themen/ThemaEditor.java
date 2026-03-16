package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.profil.ProfilDateiValue;
import com.awesome.thesis.logic.domain.model.profil.ProfilThemaValue;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaDateiValue;
import com.awesome.thesis.logic.domain.model.themen.ThemaFachgebiet;
import com.awesome.thesis.logic.domain.model.themen.ThemaLink;
import com.awesome.thesis.logic.domain.model.themen.ThemaVoraussetzung;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Diese Klasse ist der Service von {@link Thema}.
 */
@Service
public class ThemaEditor {

  @SuppressFBWarnings(value = "EI_EXPOSE_REP",
      justification = "Spring Konstruktor Injection")
  private final ThemaRepoI repository;

  @SuppressFBWarnings(value = "EI_EXPOSE_REP",
      justification = "Spring Konstruktor Injection")
  private final ProfilEditor profilEditor;

  @SuppressFBWarnings(value = "EI_EXPOSE_REP",
      justification = "Spring Konstruktor Injection")
  private final FachgebieteEditor fachEditor;

  /**
   * Konstruktor für Konstruktor-Injection mit den nötigen Dependencies.
   *
   * @param repository Repository von {@link Thema}.
   * @param profilEditor Service vom Profil.
   * @param fachEditor Service von Fachgebiet.
   */
  public ThemaEditor(ThemaRepoI repository, ProfilEditor profilEditor,
      FachgebieteEditor fachEditor) {
    this.repository = repository;
    this.profilEditor = profilEditor;
    this.fachEditor = fachEditor;
  }

  /**
   * Fügt einen Link hinzu.
   *
   * @param id Die Id des Themas.
   * @param url Die Url.
   * @param urlBeschreibung Die Beschreibung der Url.
   */
  public void addLink(Integer id, String url, String urlBeschreibung) {
    Thema thema = getThema(id);
    ThemaLink link = new ThemaLink(url, urlBeschreibung);
    thema.addUrl(link);
    repository.save(thema);
  }

  /**
   * Löscht einen Link.
   *
   * @param id Die Id des Themas.
   * @param link Der Link, der gelöscht werden soll.
   */
  public void removeLink(Integer id, ThemaLink link) {
    Thema thema = getThema(id);
    thema.removeUrl(link);
    repository.save(thema);
  }

  /**
   * Bearbeitet den Titel.
   *
   * @param profilId Die Id vom Profil, wozu das Thema gehört.
   * @param id Die Id des Themas.
   * @param titel Der Titel, der gesetzt werden soll.
   */
  public void editTitel(int profilId, Integer id, String titel) {
    profilEditor.addThema(profilId, id, titel);
    Thema thema = getThema(id);
    thema.setTitel(titel);
    repository.save(thema);
  }

  /**
   * Bearbeitet die Beschreibung des Themas.
   *
   * @param id Die Id des Themas.
   * @param beschreibung Die Beschreibung des Themas.
   */
  public void editBeschreibung(Integer id, String beschreibung) {
    Thema thema = getThema(id);
    thema.setBeschreibung(beschreibung);
    repository.save(thema);
  }

  /**
   * Fügt ein Thema in die Datenbank hinzu falls noch nicht vorhanden, sonst ein Update.
   *
   * @param thema Das Thema, das hinzugefügt werden soll.
   * @param profilId Die Id des Profils, wozu das Thema gehört.
   */
  public Thema addThema(Thema thema, int profilId) {
    Thema saved = repository.save(thema);
    profilEditor.addThema(profilId, saved.getId(), saved.getTitel());
    return saved;
  }

  /**
   * Fragt nach einem Thema aus der Datenbank an.
   *
   * @param id Die Id des Themas, wonach gesucht wird.
   * @return Das Thema, wonach gesucht wird, sonst eine Exception,
   *         dass das gesuchte Thema nicht vorhanden ist.
   */
  public Thema getThema(Integer id) {
    if (repository.containsKey(id)) {
      return repository.get(id);
    } else {
      throw new NoSuchElementException("Thema with id " + id + "not found");
    }
  }

  public List<Thema> getAll() {
    return repository.getThemen();
  }

  /**
   * Checkt, ob ein bestimmtes Profil das Thema bearbeiten darf.
   *
   * @param profilId Die Id des Profils.
   * @param themaId Die Id des Themas.
   * @return True, falls das Profil das Thema editieren darf, sonst false.
   */
  public boolean allowedEdit(Integer profilId, Integer themaId) {
    Thema thema = getThema(themaId);
    return Objects.equals(profilId, thema.getProfilId());
  }

  /**
   * Löscht ein Thema aus der Datenbank.
   *
   * @param id Die Id des Themas, welches gelöscht werden soll.
   * @param profilId Die Id des Profils, wozu das Thema gehört.
   */
  public void deleteThema(Integer id, Integer profilId) {
    profilEditor.removeThema(profilId, id);
    repository.delete(id);
  }

  /**
   * Löscht eine Voraussetzung aus allen Themen.
   *
   * @param v Die Voraussetzung, die gelöscht werden soll.
   */
  public void removeAllVoraussetzung(Voraussetzung v) {
    List<Thema> themen = repository.getThemen();
    for (Thema t : themen) {
      t.removeVoraussetzung(
          new ThemaVoraussetzung(v.voraussetzung())
      );
      repository.save(t);
    }
  }

  /**
   * Ersetzt die vorhandenen Voraussetzungen im Thema durch neue Voraussetzungen.
   *
   * @param id Die Id des Themas.
   * @param voraussetzungen Die Voraussetzungen.
   */
  public void updateVoraussetzungen(Integer id, Set<String> voraussetzungen) {
    Set<ThemaVoraussetzung> safeVoraussetzungen = mapToThemaVoraussetzung(voraussetzungen);
    Thema thema = getThema(id);
    thema.updateVoraussetzungen(safeVoraussetzungen);
    repository.save(thema);
  }

  /**
   * Fügt ein Fachgebiet hinzu.
   *
   * @param id Die Id des Themas.
   * @param fachgebiet Das Fachgebiet, das gelöscht werden soll.
   */
  public void addFachgebiet(Integer id, String fachgebiet) {
    Thema thema = getThema(id);
    fachEditor.add(fachgebiet);
    thema.addFachgebiet(new ThemaFachgebiet(fachgebiet));
    repository.save(thema);
  }

  /**
   * Löscht ein Fachgebiet aus einem Thema.
   *
   * @param id Die Id des Themas.
   * @param fachgebiet Das Fachgebiet, dass gelöscht werden soll.
   */
  public void removeFachgebiet(Integer id, String fachgebiet) {
    Thema thema = getThema(id);
    fachEditor.remove(fachgebiet);
    thema.removeFachgebiet(new ThemaFachgebiet(fachgebiet));
    repository.save(thema);
  }

  /**
   * Fügt eine Datei zu einem Thema hinzu.
   *
   * @param id Die Id des Themas.
   * @param dateiId Die Datei Id.
   * @param title Der Titel der Datei.
   * @param description Die Beschreibung der Datei.
   */
  public void addDatei(Integer id, String dateiId, String title, String description) {
    Thema thema = getThema(id);
    ThemaDateiValue datei = new ThemaDateiValue(dateiId, title, description);
    thema.addDatei(datei);
    repository.save(thema);
  }

  /**
   * Formt ein Set von String zu einem Set von ThemaVoraussetzung um.
   *
   * @param voraussetzungen Das eingegebene Set.
   * @return Set von ThemaVoraussetzung.
   */
  private Set<ThemaVoraussetzung> mapToThemaVoraussetzung(Set<String> voraussetzungen) {
    if (!(voraussetzungen == null)) {
      return voraussetzungen.stream()
          .map(ThemaVoraussetzung::new)
          .collect(Collectors.toSet());
    }
    return Set.of();
  }

  /**
   * Eine Liste von Themen, welche die eingegebenen Voraussetzungen und Interessen besitzen.
   *
   * @param voraussetzungen Die Voraussetzungen.
   * @param interessen Die Interessen.
   * @return Eine Liste von passenden Themen.
   */
  public List<Thema> getFitting(Set<String> voraussetzungen, Set<String> interessen) {
    Set<ThemaFachgebiet> themaFachgebiet = mapToThemaFachgebiet(interessen);
    Set<ThemaVoraussetzung> themaVoraussetzungen = mapToThemaVoraussetzung(voraussetzungen);
    return getAll().stream()
        .filter(e -> e.fitsRequirements(themaVoraussetzungen, themaFachgebiet))
        .toList();
  }

  /**
   * Sortiert Themen nach der Anzahl der Interessen und Voraussetzungen, die eingegeben wurden.
   *
   * @param voraussetzungen Die Voraussetzungen.
   * @param interessen Die Interessen.
   * @return Eine Liste von Themen sortiert nach der Anzahl
   *         der Interessen und Voraussetzungen, die eingegeben wurden.
   */
  public List<Thema> sortRang(Set<String> voraussetzungen, Set<String> interessen) {
    Set<ThemaVoraussetzung> themaVoraussetzungen = mapToThemaVoraussetzung(voraussetzungen);
    Set<ThemaFachgebiet> themaFachgebiet = mapToThemaFachgebiet(interessen);
    return getAll().stream()
        .filter(e -> e.calcRang(themaVoraussetzungen, themaFachgebiet) != -1)
        .sorted(Comparator.comparingLong(
            (Thema thema) -> thema.calcRang(themaVoraussetzungen, themaFachgebiet)).reversed())
        .toList();
  }

  private Set<ThemaFachgebiet> mapToThemaFachgebiet(Set<String> interessen) {
    if (!(interessen == null)) {
      return interessen.stream().map(ThemaFachgebiet::new).collect(Collectors.toSet());
    } else {
      return Set.of();
    }
  }

  private Set<Voraussetzung> mapToVoraussetzung(Set<ThemaVoraussetzung> themaVoraussetzung) {
    return themaVoraussetzung.stream()
        .map(e -> new Voraussetzung(e.voraussetzung()))
        .collect(Collectors.toSet());
  }

  /**
   * Lädt alle Voraussetzungen von einem Thema.
   *
   * @param id Die Id des Themas.
   * @return Ein Set von Voraussetzungen passend zum Thema.
   */
  public Set<Voraussetzung> getVoraussetzungen(Integer id) {
    Thema thema = getThema(id);
    return mapToVoraussetzung(thema.getVoraussetzungen());
  }

  /**
   * Löscht eine Voraussetzung für alle Themen.
   *
   * @param voraussetzung Die Voraussetzungen, die gelöscht werden sollen.
   */
  public void removeVoraussetzungForAll(String voraussetzung) {
    ThemaVoraussetzung themaVor = new ThemaVoraussetzung(voraussetzung);
    List<Thema> list = getAll().stream().filter(e -> e.hasVoraussetzung(themaVor)).toList();
    for (Thema thema : list) {
      thema.removeVoraussetzung(themaVor);
      repository.save(thema);
    }
  }

  /**
   * Löscht eine Datei von einem Thema.
   *
   * @param themaId Die Id des Themas
   * @param dateiId Die Id der Datei
   */
  public void removeDatei(int themaId, String dateiId) {
    Thema thema = getThema(themaId);
    thema.removeDatei(new ThemaDateiValue(dateiId, "", ""));
    repository.save(thema);
  }

  /**
   * Löscht alle Themen von einem Profil.
   *
   * @param profilId Profil-ID.
   */
  public void deleteThemaFromProfil(int profilId) {
    Profil profil = profilEditor.get(profilId);
    Set<ProfilThemaValue> themen = profil.getThemen();
    themen.stream()
        .map(ProfilThemaValue::id)
        .forEach(e -> deleteThema(e, profilId));
  }
}
