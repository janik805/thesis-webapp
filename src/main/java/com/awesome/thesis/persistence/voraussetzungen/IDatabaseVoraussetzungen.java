package com.awesome.thesis.persistence.voraussetzungen;

import com.awesome.thesis.logic.domain.model.themen.Voraussetzung;

import java.util.List;

public interface IDatabaseVoraussetzungen {
        void add(Voraussetzung voraussetzung);
        void delete(Voraussetzung voraussetzung);
        List<Voraussetzung> getAll();
}
