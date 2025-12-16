package com.awesome.thesis.logic.application.service.profiles;

import com.awesome.thesis.logic.domain.model.profil.Profil;

import java.util.List;

public interface IProfileRepo {
    Profil get(String id);

    boolean containsKey(String id);

    String save(String id, Profil profil);

    void update(String key, Profil profil);

    void delete(String id);

    List<Profil> getAll();
}
