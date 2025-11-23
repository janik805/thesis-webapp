package com.awesome.thesis.profiles.profil;

public record Kontakt(String label, String wert, Kontaktart kontaktart) {
    public String getHref() {
        return String.format(kontaktart.getHref(), wert);
    }
}
