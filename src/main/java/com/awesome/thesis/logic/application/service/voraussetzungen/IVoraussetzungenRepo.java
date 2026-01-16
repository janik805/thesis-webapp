package com.awesome.thesis.logic.application.service.voraussetzungen;

import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;

import java.util.Set;

public interface IVoraussetzungenRepo {
    void add(Voraussetzung voraussetzung);
    void remove(Voraussetzung voraussetzung);
    Set<Voraussetzung> getAll();
}
