package com.awesome.thesis.controller.dto;

/**
 * Record zum Laden eines individuellen Navbar-Buttons.
 *
 * @param label Text von Button
 * @param url Url zu der Link-Button verlängert
 */
public record NavbarButtonDto(String label, String url) {
}
