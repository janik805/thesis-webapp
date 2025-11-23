package com.awesome.thesis.profiles;

import com.awesome.thesis.profiles.profil.Profil;
import org.springframework.stereotype.Service;

@Service
public class ProfilEditor {
    Profile profile;

    public ProfilEditor(Profile profile) {
        this.profile = profile;
    }

    public void edit(String id) {
        Profil profil = profile.get(id);
        //TODO: implementation
        profile.save(profil);
    }

    public void add(Profil profil) {
        if (profil.getId() != null) {
            if (profile.containsKey(profil.getId())) {
                profile.update(profil.getId(), profil);
            }
        }
        profil.setId(profile.save(profil));
    }

    public Profil get(String id) {
        if(profile.containsKey(id)) {
            return profile.get(id);
        }
        throw new IllegalArgumentException("No such id " + id);
    }
}
