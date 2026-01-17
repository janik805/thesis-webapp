package com.awesome.thesis.logic.application.service.voraussetzungen;


import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VoraussetzungenEditor{
    private final IVoraussetzungenRepo repo;

    public VoraussetzungenEditor(IVoraussetzungenRepo repo) {
        this.repo = repo;
    }

    public void add(Voraussetzung voraussetzung) {
        repo.add(voraussetzung);
    }

    public List<Voraussetzung> getAll() {
        Set<Voraussetzung> set = repo.getAll();
        return set.stream().sorted(Comparator.comparing(Voraussetzung::getVoraussetzung)).collect(Collectors.toList());
    }

    public void remove(Voraussetzung voraussetzung) {
        repo.remove(voraussetzung);
    }

    public Set<Voraussetzung> mapToVoraussetzung(Set<String> voraussetzungen) {
        if (voraussetzungen == null) return Collections.emptySet();
        return voraussetzungen.stream()
                .map(Voraussetzung::new)
                .collect(Collectors.toSet());
    }

    public Set<String> getAllString() {
        return repo.getAll().stream().map(Voraussetzung::getVoraussetzung).collect(Collectors.toSet());
    }
}
