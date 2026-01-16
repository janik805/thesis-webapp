package com.awesome.thesis.logic.application.service.fachgebiete;

import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;

import java.util.Set;

public interface IFachgebieteRepo {
    void add(String name, Fachgebiet fachgebiet);
    void delete(String name);
    Set<Fachgebiet> getAll();
    boolean contains(String fachgebiet);
}
