package com.awesome.thesis.persistence.themen.dtos;

import org.springframework.data.relational.core.mapping.Table;

/**
 * Diese Klasse dient als technisches DTO für die Persistenz-Schicht.
 *
 * @param url url
 * @param text Beschreibender Text der Url
 */
@Table("thema_link")
public record ThemaLinkDto(String url, String text) {
}
