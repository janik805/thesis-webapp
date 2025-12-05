package com.awesome.thesis.persistence.themen;

import com.awesome.thesis.logic.domain.model.themen.Thema;

import java.util.List;

public interface IDatabaseThema {
    Thema get(String id);
    String save(Thema thema);
    boolean containsKey(String id);
    void update(String id, Thema thema);
    void delete(String id);
    List<Thema> getAll();
}
