package com.awesome.thesis.logic.domain.model.themen;

import com.awesome.thesis.annotations.AggregateValue;

@AggregateValue
public record ThemaLink (String url, String text) {
}
