package com.awesome.thesis.controller.dto;

/**
 * Record um Nutzerabhängigen Footer auf der Startseite zu laden.
 *
 * @param greeting Überschrift von Footer
 * @param description Beschreibung von Footer
 * @param url Url des link Buttons
 * @param label Text des Linkbuttons
 */
public record ProfilFooterDto(String greeting, String description, String url, String label) {
}
