package com.awesome.thesis.persistence.themen.dtos;

import org.springframework.data.relational.core.mapping.Table;

/**
 * Diese Klasse dient als technisches DTO für die Persistenz-Schicht.
 *
 * @param fachgebiet Fachgebiet als String
 */
@Table("thema_fachgebiet")
public record ThemaFachgebietDto(String fachgebiet) {
}
