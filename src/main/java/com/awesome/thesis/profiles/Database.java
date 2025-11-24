package com.awesome.thesis.profiles;

import com.awesome.thesis.profiles.profil.Profil;

import java.util.List;

interface Database {
    Profil get(String id);
    String save(Profil profil);
    boolean containsKey(String id);
    void update(String key, Profil profil);
    void delete(String key);
    List<Profil> getAll();
}
