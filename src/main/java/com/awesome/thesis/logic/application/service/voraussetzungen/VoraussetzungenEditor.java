package com.awesome.thesis.logic.application.service.voraussetzungen;


import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VoraussetzungenEditor{
    private final IVoraussetzungenRepo repo;
    private final ThemaEditor themaEditor;
    public VoraussetzungenEditor(IVoraussetzungenRepo repo, ThemaEditor themaEditor) {
        this.repo = repo;
        this.themaEditor = themaEditor;
    }

    public void add(Voraussetzung voraussetzung) {
        repo.add(voraussetzung);
    }

    public List<Voraussetzung> getAll() {
        Set<Voraussetzung> set = repo.getAll();
        return set.stream().sorted(Comparator.comparing(Voraussetzung::getVoraussetzung)).collect(Collectors.toList());
    }

    public void remove(Voraussetzung voraussetzung) {
        themaEditor.removeVoraussetzungForAll(voraussetzung.getVoraussetzung());
        repo.remove(voraussetzung);
    }

    public Set<String> getAllString() {
        return repo.getAll().stream().map(Voraussetzung::getVoraussetzung).collect(Collectors.toSet());
    }
}
