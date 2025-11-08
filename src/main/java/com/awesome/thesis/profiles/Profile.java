package com.awesome.thesis.profiles;

import com.awesome.thesis.profiles.profil.Profil;
import org.springframework.stereotype.Component;


@Component
public class Profile {
    Database database;

    public Profile(Database database) {
        this.database = database;
    }

    public Profil get(long id) {
        if(database.containsKey(id)) {
            return database.get(id);
        }
        throw new IllegalArgumentException("No such id " + id);
    }

    public void save(Profil profil) {
        if (profil.getId() != null) {
            if (database.containsKey(profil.getId())) {
                database.update(profil.getId(), profil);
            }
        }
        profil.setId(database.save(profil));
    }
}
