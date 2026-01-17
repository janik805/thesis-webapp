package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.themen.Thema;

import java.util.List;

public interface IThemaRepo {
    Integer save(Thema thema);
    void delete (Integer id);
    List<Thema> getThemen();
    Thema get(Integer id);
    void update(Integer key, Thema thema);
    boolean containsKey(Integer id);

}
