package com.awesome.thesis.logic.domain.model.themen;

import com.awesome.thesis.annotations.AggregateValue;

/**
 * Repräsentiert ein Fachgebiet innerhalb des Thema-Aggregats.
 *
 * @param fachgebiet Eingabe als String.
 */
@AggregateValue
public record ThemaFachgebiet(String fachgebiet) {
}
