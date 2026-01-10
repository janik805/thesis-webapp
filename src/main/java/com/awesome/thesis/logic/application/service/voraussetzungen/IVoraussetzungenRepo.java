package com.awesome.thesis.logic.application.service.voraussetzungen;

import com.awesome.thesis.logic.domain.model.themen.Voraussetzung;

import java.util.List;

public interface IVoraussetzungenRepo {
    void add(Voraussetzung voraussetzung);
    void remove(Voraussetzung voraussetzung);
    List<Voraussetzung> getAll();
}
