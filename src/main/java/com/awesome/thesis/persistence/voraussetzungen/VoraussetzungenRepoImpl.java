package com.awesome.thesis.persistence.voraussetzungen;

import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenRepoI;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import com.awesome.thesis.persistence.voraussetzungen.dtos.VoraussetzungDto;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * Diese Klasse ist das fachliche Repository für das {@link Voraussetzung} Aggregat
 * und ist für das Mapping zwischen domain.model und DTOs der Datenbank.
 */
@Repository
public class VoraussetzungenRepoImpl implements VoraussetzungenRepoI {

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
      justification = "Spring Konstruktor Injection")
  private final VoraussetzungenDbRepository database;

  public VoraussetzungenRepoImpl(VoraussetzungenDbRepository database) {
    this.database = database;
  }

  @Override
  public void add(Voraussetzung voraussetzung) {
    database.save(toVoraussetzungDto(voraussetzung));
  }

  @Override
  public void remove(Voraussetzung voraussetzung) {
    database.deleteById(voraussetzung.voraussetzung());
  }

  @Override
  public Set<Voraussetzung> getAll() {
    return database.findAll().stream()
        .map(this::toVoraussetzung)
        .collect(Collectors.toSet());
  }

  @Override
  public boolean contains(Voraussetzung voraussetzung) {
    return database.existsById(voraussetzung.voraussetzung());
  }

  private VoraussetzungDto toVoraussetzungDto(Voraussetzung voraussetzung) {
    return new VoraussetzungDto(voraussetzung.voraussetzung(), voraussetzung.version());
  }

  private Voraussetzung toVoraussetzung(VoraussetzungDto voraussetzung) {
    return new Voraussetzung(voraussetzung.voraussetzung(), voraussetzung.version());
  }
}
