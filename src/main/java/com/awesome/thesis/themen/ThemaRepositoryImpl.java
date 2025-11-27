package com.awesome.thesis.themen;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ThemaRepositoryImpl implements IThemaRepository {
    private final List<Thema> themen = new ArrayList<>();

    @Override
    public void addThema(Thema thema) {
        themen.add(thema);
    }

    @Override
    public void removeThema(Thema thema) {
        themen.remove(thema);
    }

    @Override
    public List<Thema> getThemen() {
        return new ArrayList<Thema>(themen);
    }

    @Override
    public Thema getThema(String titel) {
        return themen.stream()
                .filter(e -> e.getTitel().equals(titel))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Kein Thema mit Titel " + titel + " gefunden"));
    }
}
