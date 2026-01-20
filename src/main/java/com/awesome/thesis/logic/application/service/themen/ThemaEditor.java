package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaDateiValue;
import com.awesome.thesis.logic.domain.model.themen.ThemaFachgebiet;
import com.awesome.thesis.logic.domain.model.themen.ThemaLink;
import com.awesome.thesis.logic.domain.model.themen.ThemaVoraussetzung;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ThemaEditor {

  IThemaRepo repository;

  private final ProfilEditor profilEditor;

  private final FachgebieteEditor fachEditor;

  public ThemaEditor(IThemaRepo repository, ProfilEditor profilEditor,
      FachgebieteEditor fachEditor) {
    this.repository = repository;
    this.profilEditor = profilEditor;
    this.fachEditor = fachEditor;
  }

  public void addLink(Integer id, String url, String urlBeschreibung) {
    Thema thema = getThema(id);
    ThemaLink link = new ThemaLink(url, urlBeschreibung);
    thema.addUrl(link);
    repository.update(id, thema);
  }

  public void removeLink(Integer id, ThemaLink link) {
    Thema thema = getThema(id);
    thema.removeUrl(link);
    repository.update(id, thema);
  }

  public void editTitel(int profilId, Integer id, String titel) {
    profilEditor.addThema(profilId, id, titel);
    Thema thema = getThema(id);
    thema.setTitel(titel);
    repository.update(id, thema);
  }

  public void editBeschreibung(Integer id, String beschreibung) {
    Thema thema = getThema(id);
    thema.setBeschreibung(beschreibung);
    repository.update(id, thema);
  }

  public void addThema(Thema thema, int profilId) {
    if (thema.getId() != null) {
      if (repository.containsKey(thema.getId())) {
        repository.update(thema.getId(), thema);
      }
    } else {
      repository.save(thema);
    }
    profilEditor.addThema(profilId, thema.getId(), thema.getTitel());
  }

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

  public boolean allowedEdit(long profilId, Thema thema) {
    return profilId == thema.getProfilId();
  }

  public void deleteThema(Integer id, Integer profilId) {
    profilEditor.removeThema(profilId, id);
    repository.delete(id);
  }

  public void removeAllVoraussetzung(Voraussetzung v) {
    List<Thema> themen = repository.getThemen();
    for (Thema t : themen) {
      t.removeVoraussetzung(
          new ThemaVoraussetzung(v.getVoraussetzung())
      );
      repository.update(t.getId(), t);
    }
  }

  public void updateVoraussetzungen(Integer id, Set<String> voraussetzungen) {
    Set<ThemaVoraussetzung> safeVoraussetzungen = mapToThemaVoraussetzung(voraussetzungen);
    Thema thema = getThema(id);
    thema.updateVoraussetzungen(safeVoraussetzungen);
    repository.update(thema.getId(), thema);
  }

  public void addFachgebiet(Integer id, String fachgebiet) {
    Thema thema = getThema(id);
    fachEditor.add(fachgebiet);
    thema.addFachgebiet(new ThemaFachgebiet(fachgebiet));
    repository.update(thema.getId(), thema);
  }

  public void removeFachgebiet(Integer id, String fachgebiet) {
    Thema thema = getThema(id);
    fachEditor.remove(fachgebiet);
    thema.removeFachgebiet(new ThemaFachgebiet(fachgebiet));
    repository.update(thema.getId(), thema);
  }

  public void addDatei(Integer id, ThemaDateiValue datei) {
    Thema thema = getThema(id);
    thema.addDatei(datei);
    repository.update(id, thema);
  }

  public Set<ThemaVoraussetzung> mapToThemaVoraussetzung(Set<String> voraussetzungen) {
    if (!(voraussetzungen == null)) {
      return voraussetzungen.stream()
          .map(ThemaVoraussetzung::new)
          .collect(Collectors.toSet());
    }
    return Set.of();
  }

  public List<Thema> getFitting(Set<String> voraussetzungen, Set<String> interessen) {
    Set<ThemaFachgebiet> themaFachgebiet = mapToThemaFachgebiet(interessen);
    Set<ThemaVoraussetzung> themaVoraussetzungen = mapToThemaVoraussetzung(voraussetzungen);
    return getAll().stream()
        .filter(e -> e.fitsRequirements(themaVoraussetzungen, themaFachgebiet))
        .toList();
  }

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

  public Set<Voraussetzung> getVoraussetzungen(Integer id) {
    Thema thema = getThema(id);
    return mapToVoraussetzung(thema.getVoraussetzungen());
  }

  public void removeVoraussetzungForAll(String voraussetzung) {
    ThemaVoraussetzung themaVor = new ThemaVoraussetzung(voraussetzung);
    List<Thema> list = getAll().stream().filter(e -> e.hasVoraussetzung(themaVor)).toList();
    for (Thema thema : list) {
      thema.removeVoraussetzung(themaVor);
      repository.update(thema.getId(), thema);
    }
  }
}
