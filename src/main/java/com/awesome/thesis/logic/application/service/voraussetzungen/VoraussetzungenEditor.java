package com.awesome.thesis.logic.application.service.voraussetzungen;


import com.awesome.thesis.logic.domain.model.themen.Voraussetzung;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
        return repo.getAll();
    }

    public void remove(Voraussetzung voraussetzung) {
        repo.remove(voraussetzung);
    }
}
