package com.awesome.thesis.thema;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ThemaRepositoryImpl {
    private final List<Thema> themen = new ArrayList<>();

    public void addThema(Thema thema) {
        themen.add(thema);
    }

    public void removeThema(Thema thema) {
        themen.remove(thema);
    }

    public List<Thema> getThemen() {
        return themen;
    }

    public Thema getThema(String titel) {
        return themen.stream()
                .filter(e -> e.getTitel().equals(titel))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Kein Thema mit Titel " + titel + " gefunden"));
    }

}
