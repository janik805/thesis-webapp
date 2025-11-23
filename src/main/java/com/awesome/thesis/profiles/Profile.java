package com.awesome.thesis.profiles;

import com.awesome.thesis.profiles.profil.Profil;
import org.springframework.stereotype.Repository;

@Repository
public class Profile {
    Database database;

    public Profile(Database database) {
        this.database = database;
    }

    public Profil get(String id) {
        return database.get(id);
    }

    public boolean containsKey(String id) {
        return database.containsKey(id);
    }

    public String save(Profil profil) {
        return database.save(profil);
    }

    public void update(String key, Profil profil) {
        database.update(key, profil);
    }

    public void delete(String id) {
        database.delete(id);
    }
}
