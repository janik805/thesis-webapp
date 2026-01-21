package com.awesome.thesis.persistence.themen.dtos;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Diese Klasse dient als technisches DTO für die Persistenz-Schicht.
 *
 * @param id Schlüssel des Themas
 * @param titel Titel des Themas
 * @param beschreibung Beschreibung des Themas
 * @param profilId Die profilID des Themas
 * @param links Die Links des Themas als Set
 * @param voraussetzungen Die Voraussetzungen des Themas als Set
 * @param fachgebiete Die Fachgebiete des Themas als Set
 * @param dateien Die Dateien des Themas als Set
 */
@Table("thema")
public record ThemaDto(@Id Integer id,
                       String titel,
                       String beschreibung,
                       int profilId,
                       Set<ThemaLinkDto> links,
                       Set<ThemaVoraussetzungDto> voraussetzungen,
                       Set<ThemaFachgebietDto> fachgebiete,
                       Set<ThemaDateiValueDto> dateien) {

  /**
   * Konstruktor von ThemaDto.
   *
   * @param id Die Id des Themas.
   * @param titel Der Titel des Themas.
   * @param beschreibung Die Beschreibung des Themas.
   * @param profilId Die Id des Profils, wozu das Thema gehört.
   * @param links Die Links vom Thema.
   * @param voraussetzungen Die Voraussetzungen des Themas.
   * @param fachgebiete Die Fachgebiete des Themas.
   * @param dateien Dei Dateien des Themas.
   */
  public ThemaDto(Integer id, String titel, String beschreibung, int profilId,
      Set<ThemaLinkDto> links,
      Set<ThemaVoraussetzungDto> voraussetzungen,
      Set<ThemaFachgebietDto> fachgebiete,
      Set<ThemaDateiValueDto> dateien) {
    this.id = id;
    this.titel = titel;
    this.beschreibung = beschreibung;
    this.profilId = profilId;
    this.links = new HashSet<>(links);
    this.voraussetzungen = new HashSet<>(voraussetzungen);
    this.fachgebiete = new HashSet<>(fachgebiete);
    this.dateien = new HashSet<>(dateien);
  }


  @Override
  public Set<ThemaLinkDto> links() {
    return Set.copyOf(links);
  }

  @Override
  public Set<ThemaVoraussetzungDto> voraussetzungen() {
    return Set.copyOf(voraussetzungen);
  }

  @Override
  public Set<ThemaFachgebietDto> fachgebiete() {
    return Set.copyOf(fachgebiete);
  }

  @Override
  public Set<ThemaDateiValueDto> dateien() {
    return Set.copyOf(dateien);
  }
}
