package com.awesome.thesis.logic.domain.model.themen;

import com.awesome.thesis.annotations.AggregateValue;

/**
 * Repräsentiert eine Voraussetzung innerhalb des Thema-Aggregats.
 *
 * @param voraussetzung Eingabe als String.
 */
@AggregateValue
public record ThemaVoraussetzung(String voraussetzung){
}
