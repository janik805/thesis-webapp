package com.awesome.thesis.persistence.themen;

import com.awesome.thesis.logic.domain.model.themen.Thema;

import java.util.List;

public interface IDatabaseThema {
    Thema get(Integer id);
    Integer save(Thema thema);
    boolean containsKey(Integer id);
    void update(Integer id, Thema thema);
    void delete(Integer id);
    List<Thema> getAll();
}
