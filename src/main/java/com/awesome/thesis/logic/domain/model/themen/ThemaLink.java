package com.awesome.thesis.logic.domain.model.themen;

import com.awesome.thesis.annotations.AggregateValue;

/**
 * Repräsentiert eine Url innerhalb des Thema-Aggregats.
 *
 * @param url Der Link.
 * @param text Der beschreibende Text passend zum Link.
 */
@AggregateValue
public record ThemaLink(String url, String text) {
}
