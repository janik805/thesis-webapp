package com.awesome.thesis.persistence.voraussetzungen;

import com.awesome.thesis.logic.application.service.voraussetzungen.IVoraussetzungenRepo;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * Diese Klasse ist das fachliche Repository für das {@link Voraussetzung} Aggregat
 * und ist für das Mapping zwischen domain.model und DTOs der Datenbank.
 */
@Repository
public class VoraussetzungenRepoImpl implements IVoraussetzungenRepo {

  private final VoraussetzungenDbRepository database;

  public VoraussetzungenRepoImpl(VoraussetzungenDbRepository database) {
    this.database = database;
  }

  @Override
  public void add(Voraussetzung voraussetzung) {
    database.insert(voraussetzung.getVoraussetzung());
  }

  @Override
  public void remove(Voraussetzung voraussetzung) {
    database.deleteById(voraussetzung.getVoraussetzung());
  }

  @Override
  public Set<Voraussetzung> getAll() {
    return database.findAll().stream()
        .map(e -> new Voraussetzung(e.voraussetzung()))
        .collect(Collectors.toSet());
  }

  @Override
  public boolean contains(Voraussetzung voraussetzung) {
    return database.existsById(voraussetzung.getVoraussetzung());
  }
}
