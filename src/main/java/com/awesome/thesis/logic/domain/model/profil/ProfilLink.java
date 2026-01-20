package com.awesome.thesis.logic.domain.model.profil;

import com.awesome.thesis.annotations.AggregateValue;

/**
 * Dieser Record dient der fachlichen Speicherung von Links.
 *
 * @param url Speicherung der Url
 * @param text Beschreibung der Url
 */
@AggregateValue
public record ProfilLink(String url, String text) {
}