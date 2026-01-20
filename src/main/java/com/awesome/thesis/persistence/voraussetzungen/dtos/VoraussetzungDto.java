package com.awesome.thesis.persistence.voraussetzungen.dtos;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Diese Klasse dient als technisches DTO für die Persistenz-Schicht.
 *
 * @param voraussetzung Voraussetzung als String
 */
@Table("voraussetzung")
public record VoraussetzungDto(@Id String voraussetzung) {
}
