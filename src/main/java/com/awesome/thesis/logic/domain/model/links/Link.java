package com.awesome.thesis.logic.domain.model.links;

import com.awesome.thesis.annotations.AggregateRoot;

@AggregateRoot
public record Link (String url, String text) {
}