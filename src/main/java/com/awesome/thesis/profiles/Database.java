package com.awesome.thesis.profiles;

import com.awesome.thesis.profiles.profil.Profil;

public interface Database {
    Profil get(String id);
    String save(Profil profil);
    boolean containsKey(String id);
    void update(String key, Profil profil);
    void delete(String key);
}
