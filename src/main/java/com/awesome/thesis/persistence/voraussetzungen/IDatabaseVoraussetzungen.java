package com.awesome.thesis.persistence.voraussetzungen;

import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;

import java.util.Set;

public interface IDatabaseVoraussetzungen {
        void add(Voraussetzung voraussetzung);
        void delete(Voraussetzung voraussetzung);
        Set<Voraussetzung> getAll();
}
