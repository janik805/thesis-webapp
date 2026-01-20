package com.awesome.thesis.persistence.themen.dtos;

import org.springframework.data.relational.core.mapping.Table;

/**
 * Diese Klasse dient als technisches DTO für die Persistenz-Schicht.
 *
 * @param id Der Schlüssel des Themas
 * @param name Titel des Themas
 * @param beschreibung Beschreibung des Themas
 */
@Table("thema_datei_value")
public record ThemaDateiValueDto(String id, String name, String beschreibung) {
}
