package com.awesome.thesis.profiles;

import com.awesome.thesis.profiles.profil.Profil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class Profile implements ProfileInterface {
    Database database;

    public Profile(Database database) {
        this.database = database;
    }

    @Override
    public Profil get(String id) {
        return database.get(id);
    }

    @Override
    public boolean containsKey(String id) {
        return database.containsKey(id);
    }

    @Override
    public String save(Profil profil) {
        return database.save(profil);
    }

    @Override
    public void update(String key, Profil profil) {
        database.update(key, profil);
    }

    @Override
    public void delete(String id) {
        database.delete(id);
    }

    @Override
    public List<Profil> getAll() {
        return database.getAll();
    }
}
