package com.awesome.thesis.logic.application.service;

import com.awesome.thesis.logic.domain.model.profil.Kontakt;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfilEditor {
    private final IProfileRepo profile;

    public ProfilEditor(IProfileRepo profile) {
        this.profile = profile;
    }

    public void addKontakt(String id, Kontakt kontakt) {
        Profil profil = get(id);
        profil.addKontakt(kontakt);
        profile.update(id, profil);
    }

    public void editName(String id, String name) {
        Profil profil = get(id);
        profil.setName(name);
        profile.update(id, profil);
    }

    public void removeKontakt(String id, Kontakt kontakt) {
        Profil profil = get(id);
        profil.removeKontakt(kontakt);
        profile.update(id, profil);
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

    public List<Profil> getAll() {
        return profile.getAll();
    }
}
