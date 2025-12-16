package com.awesome.thesis.persistence.profiles;

import com.awesome.thesis.logic.domain.model.profil.Profil;

import java.util.List;

public interface IDatabaseProfile {
    Profil get(String id);
    String save(String id, Profil profil);
    boolean containsKey(String id);
    void update(String key, Profil profil);
    void delete(String key);
    List<Profil> getAll();
}
