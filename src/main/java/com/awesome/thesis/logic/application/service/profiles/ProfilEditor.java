package com.awesome.thesis.logic.application.service.profiles;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.domain.model.profil.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProfilEditor {
    private final IProfileRepo profile;
    private final FachgebieteEditor fachgebieteEditor;

    public ProfilEditor(IProfileRepo profile, FachgebieteEditor fachgebieteEditor) {
        this.profile = profile;
        this.fachgebieteEditor = fachgebieteEditor;
    }

    public List<Profil> getAll() {
        return profile.getAll();
    }

    public Profil get(int id) {
        if (profile.containsKey(id)) {
            return profile.get(id);
        }
        throw new IllegalArgumentException("No such id " + id);
    }

    public List<Profil> getFitting(Set<String> interessen) {
        if (interessen == null || interessen.isEmpty()) {
            return getAll();
        }
        return profile.getAll().stream()
                .filter(p -> p.fitsInterests(interessen))
                .toList();
    }

    public List<Profil> getMatching(Set<String> interessen) {
        if (interessen == null || interessen.isEmpty()) {
            return getAll();
        }
        return profile.getAll().stream()
                .sorted((profil1, profil2) ->
                        Long.compare(
                                profil2.compRank(interessen),
                                profil1.compRank(interessen)
                        ))
                .toList();
    }

    public boolean contains(int id) {
        return profile.containsKey(id);
    }

    public void create(int id, String name) {
        if (!profile.containsKey(id)) {
            Profil profil = new Profil(id, name);
            profile.save(profil);
        }
    }

    public void delete(int id) {
        profile.delete(id);
    }

    public void editName(int id, String name) {
        Profil profil = get(id);
        profil.setName(name);
        profile.update(profil);
    }

    public void addEmail(int id, String label, String wert) {
        Profil profil = get(id);
        profil.addEmail(label, wert);
        profile.update(profil);
    }

    public void addTel(int id, String label, String wert) {
        Profil profil = get(id);
        profil.addTel(label, wert);
        profile.update(profil);
    }

    public void removeKontakt(int id, ProfilKontakt profilKontakt) {
        Profil profil = get(id);
        profil.removeKontakt(profilKontakt);
        profile.update(profil);
    }

    public void addFachgebiet(int id, String fachgebiet) {
        Profil profil = get(id);
        profil.addFachgebiet(fachgebiet);
        fachgebieteEditor.add(fachgebiet);
        profile.update(profil);
    }

    public void removeFachgebiet(int id, String fachgebiet) {
        Profil profil = get(id);
        profil.removeFachgebiet(fachgebiet);
        profile.update(profil);
        fachgebieteEditor.remove(fachgebiet);
    }

    public void addLink(int id, String url, String urlBeschreibung) {
        Profil profil = get(id);
        ProfilLink link = new ProfilLink(url, urlBeschreibung);
        profil.addLink(link);
        profile.update(profil);
    }

    public void removeLink(int id, ProfilLink link) {
        Profil profil = get(id);
        profil.removeLink(link);
        profile.update(profil);
    }

    public void addThema(int id, Integer themaId, String name) {
        Profil profil = get(id);
        profil.addThema(new ProfilThemaValue(themaId, name));
        profile.update(profil);
    }

    public void removeThema(int id, Integer themaId) {
        Profil profil = get(id);
        profil.removeThema(new ProfilThemaValue(themaId, ""));
        profile.update(profil);
    }

    public void addDatei(int id, String dateiId, String name, String beschreibung) {
        Profil profil = get(id);
        profil.addDatei(new ProfilDateiValue(dateiId, name, beschreibung));
        profile.update(profil);
    }

    public void removeDatei(int id, String dateiId) {
        Profil profil = get(id);
        profil.removeDatei(new ProfilDateiValue(dateiId, "", ""));
        profile.update(profil);
    }
}
