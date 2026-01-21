package com.awesome.thesis.logic.application.service.voraussetzungen;

import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Diese Klasse ist der Service von {@link Voraussetzung}.
 */
@Service
public class VoraussetzungenEditor {

  private final VoraussetzungenRepoI repo;
  private final ThemaEditor themaEditor;

  /**
   * Service für Voraussetzungen.
   *
   * @param repo Die Schnittstelle zwischen Applikation und Persistenzschicht.
   * @param themaEditor Service, der verwendet wird, um bei Themen Voraussetzungen zu löschen.
   */
  public VoraussetzungenEditor(VoraussetzungenRepoI repo, ThemaEditor themaEditor) {
    this.repo = repo;
    this.themaEditor = themaEditor;
  }

  /**
   * Fügt eine Voraussetzung hinzu, falls sie nicht schon in der Datenbank vorhanden ist.
   *
   * @param voraussetzung Die Eingabe, die gespeichert werden soll.
   */
  public void add(Voraussetzung voraussetzung) {
    if (!repo.contains(voraussetzung)) {
      repo.add(voraussetzung);
    }
  }

  /**
   * Fragt nach allen Voraussetzungen aus dem Repository an.
   *
   * @return Alle Voraussetzungen, die in der Datenbank gespeichert sind.
   */
  public List<Voraussetzung> getAll() {
    Set<Voraussetzung> set = repo.getAll();
    return set.stream().sorted(Comparator.comparing(Voraussetzung::getVoraussetzung))
        .collect(Collectors.toList());
  }

  /**
   * Löscht eine Voraussetzung aus der Datenbank und aus allen Themen.
   *
   * @param voraussetzung Die Voraussetzung, die gelöscht werden sollte.
   */
  public void remove(Voraussetzung voraussetzung) {
    themaEditor.removeVoraussetzungForAll(voraussetzung.getVoraussetzung());
    repo.remove(voraussetzung);
  }

  public Set<String> getAllString() {
    return repo.getAll().stream().map(Voraussetzung::getVoraussetzung).collect(Collectors.toSet());
  }
}
