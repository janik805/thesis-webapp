package com.awesome.thesis.persistence.themen;

import com.awesome.thesis.logic.application.service.themen.IThemaRepo;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaDateiValue;
import com.awesome.thesis.logic.domain.model.themen.ThemaFachgebiet;
import com.awesome.thesis.logic.domain.model.themen.ThemaLink;
import com.awesome.thesis.logic.domain.model.themen.ThemaVoraussetzung;
import com.awesome.thesis.persistence.themen.dtos.ThemaDateiValueDto;
import com.awesome.thesis.persistence.themen.dtos.ThemaDto;
import com.awesome.thesis.persistence.themen.dtos.ThemaFachgebietDto;
import com.awesome.thesis.persistence.themen.dtos.ThemaLinkDto;
import com.awesome.thesis.persistence.themen.dtos.ThemaVoraussetzungDto;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * Diese Klasse dient als fachliches Repository für das Thema Aggregat
 * und ist für das Mapping zwischen domain.model und DTOs der Datenbank.
 */
@Repository
public class ThemaRepoImpl implements IThemaRepo {

  ThemenDbRepository database;

  public ThemaRepoImpl(ThemenDbRepository database) {
    this.database = database;
  }

  @Override
  public void save(Thema thema) {
    database.save(thema);
  }

  public boolean containsKey(Integer id) {
    return database.existsById(id);
  }

  @Override
  public void delete(Integer id) {
    database.deleteById(id);
  }

  @Override
  public List<Thema> getThemen() {
    return database.findAll().stream()
        .map(this::toThema)
        .toList();
  }

  @Override
  public Thema get(int id) {
    return toThema(database.findById(id));
  }

  @Override
  public void update(Integer id, Thema thema) {
    database.save(thema);
  }

  // Thema <--> ThemaDTO

  private Set<ThemaDateiValueDto> toThemaDateiValueDto(Set<ThemaDateiValue> dateiValue) {
    return dateiValue.stream()
        .map(e -> new ThemaDateiValueDto(e.id(), e.name(), e.beschreibung()))
        .collect(Collectors.toSet());
  }

  private Set<ThemaDateiValue> toThemaDateiValue(Set<ThemaDateiValueDto> dateiValue) {
    return dateiValue.stream()
        .map(e -> new ThemaDateiValue(e.id(), e.name(), e.beschreibung()))
        .collect(Collectors.toSet());
  }

  private Set<ThemaFachgebietDto> toThemaFachgebietDto(Set<ThemaFachgebiet> fachgebiet) {
    return fachgebiet.stream()
        .map(e -> new ThemaFachgebietDto(e.fachgebiet()))
        .collect(Collectors.toSet());
  }

  private Set<ThemaFachgebiet> toThemaFachgebiet(Set<ThemaFachgebietDto> fachgebiet) {
    return fachgebiet.stream()
        .map(e -> new ThemaFachgebiet(e.fachgebiet()))
        .collect(Collectors.toSet());
  }

  private Set<ThemaLinkDto> toThemaLinkDto(Set<ThemaLink> themaLink) {
    return themaLink.stream()
        .map(e -> new ThemaLinkDto(e.url(), e.text()))
        .collect(Collectors.toSet());
  }

  private Set<ThemaLink> toThemaLink(Set<ThemaLinkDto> themaLink) {
    return themaLink.stream()
        .map(e -> new ThemaLink(e.url(), e.text()))
        .collect(Collectors.toSet());
  }

  private Set<ThemaVoraussetzungDto> toThemaVoraussetzungDto(
      Set<ThemaVoraussetzung> themaVoraussetzung) {
    return themaVoraussetzung.stream()
        .map(e -> new ThemaVoraussetzungDto(e.voraussetzung()))
        .collect(Collectors.toSet());
  }

  private Set<ThemaVoraussetzung> toThemaVoraussetzung(
      Set<ThemaVoraussetzungDto> themaVoraussetzung) {
    return themaVoraussetzung.stream()
        .map(e -> new ThemaVoraussetzung(e.voraussetzung()))
        .collect(Collectors.toSet());
  }

  private ThemaDto toThemaDto(Thema thema) {
    return new ThemaDto(thema.getId(),
        thema.getTitel(),
        thema.getBeschreibung(),
        thema.getProfilID(),
        toThemaLinkDto(thema.getLinks()),
        toThemaVoraussetzungDto(thema.getVoraussetzungen()),
        toThemaFachgebietDto(thema.getFachgebiete()),
        toThemaDateiValueDto(thema.getDateien()));
  }

  private Thema toThema(ThemaDto themaDto) {
    return new Thema(themaDto.id(),
        themaDto.titel(),
        themaDto.beschreibung(),
        themaDto.profilId(),
        toThemaLink(themaDto.links()),
        toThemaVoraussetzung(themaDto.voraussetzungen()),
        toThemaFachgebiet(themaDto.fachgebiete()),
        toThemaDateiValue(themaDto.dateien()));
  }
}
