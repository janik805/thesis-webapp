package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.themen.Thema;

import java.util.List;

public interface IThemaRepo {
    void save(Thema thema);
    void delete (String id);
    List<Thema> getThemen();
    Thema get(String id);
    void update(String key, Thema thema);
    boolean containsKey(String id);

}
