package com.awesome.thesis.persistence.themen.dtos;

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
}
