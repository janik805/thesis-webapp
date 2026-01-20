package com.awesome.thesis.persistence.themen.dtos;

import org.springframework.data.relational.core.mapping.Table;

/**
 * Diese Klasse dient als technisches DTO für die Persistenz-Schicht.
 *
 * @param voraussetzung Voraussetzung
 */
@Table("thema_voraussetzung")
public record ThemaVoraussetzungDto(String voraussetzung) {
}
