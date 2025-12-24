package com.awesome.thesis.logic.application.service.profiles;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.domain.model.profil.Kontakt;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfilEditor {
    private final IProfileRepo profile;

    @Autowired
    FachgebieteEditor fachgebieteEditor;

    public ProfilEditor(IProfileRepo profile) {
        this.profile = profile;
    }

    public void addEmail(long id, String label, String wert) {
        Profil profil = get(id);
        profil.addEmail(label, wert);
        profile.update(id, profil);
    }

    public void addTel(long id, String label, String wert) {
        Profil profil = get(id);
        profil.addTel(label, wert);
        profile.update(id, profil);
    }

    public void editName(long id, String name) {
        Profil profil = get(id);
        profil.setName(name);
        profile.update(id, profil);
    }

    public void removeKontakt(long id, Kontakt kontakt) {
        Profil profil = get(id);
        profil.removeKontakt(kontakt);
        profile.update(id, profil);
    }

    public void addFachgebiet(long id, String fachgebiet) {
        Profil profil = get(id);
        profil.addFachgebiet(fachgebiet);
        fachgebieteEditor.add(fachgebiet);
        profile.update(id, profil);
    }

    public void removeFachgebiet(long id, String fachgebiet) {
        Profil profil = get(id);
        profil.removeFachgebiet(fachgebiet);
        fachgebieteEditor.remove(fachgebiet);
        profile.update(id, profil);
    }

    public void add(Profil profil) {
        if (profile.containsKey(profil.getId())) {
            profile.update(profil.getId(), profil);
        } else {
            profile.save(profil.getId(), profil);
        }
    }

    public void create(long id, String name) {
        Profil profil = new Profil(id, name);
        profile.save(id, profil);
    }

    public Profil get(long id) {
        if (profile.containsKey(id)) {
            return profile.get(id);
        }
        throw new IllegalArgumentException("No such id " + id);
    }

    public boolean contains(long id) {
        return profile.containsKey(id);
    }

    public List<Profil> getAll() {
        return profile.getAll();
    }
}
