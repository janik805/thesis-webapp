package com.awesome.thesis.profiles;

import com.awesome.thesis.profiles.profil.Profil;

import java.util.List;

public interface ProfileInterface {
    Profil get(String id);

    boolean containsKey(String id);

    String save(Profil profil);

    void update(String key, Profil profil);

    void delete(String id);

    List<Profil> getAll();
}
