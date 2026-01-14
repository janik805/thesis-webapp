package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.application.dto.DateiDTO;
import com.awesome.thesis.logic.application.dto.ThemaDTO;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.Voraussetzung;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ThemaEditor {
    IThemaRepo repository;

    private final ProfilEditor profilEditor;

    public ThemaEditor(IThemaRepo repository, ProfilEditor profilEditor) {
        this.repository = repository;
        this.profilEditor = profilEditor;
    }

    public void addLink(String id, String url, String urlBeschreibung) {
        Thema thema = getThema(id);
        Link link = new Link(url, urlBeschreibung);
        thema.addUrl(link);
        repository.update(id,thema);
    }

    public void removeLink(String id, Link link) {
        Thema thema = getThema(id);
        thema.removeUrl(link);
        repository.update(id, thema);
    }

    public void editTitel(long profilID, String id, String titel) {
        profilEditor.addThema(profilID, id, titel);
        Thema thema = getThema(id);
        thema.setTitel(titel);
        repository.update(id, thema);
    }

    public void editBeschreibung(String id, String beschreibung) {
        Thema thema = getThema(id);
        thema.setBeschreibung(beschreibung);
        repository.update(id, thema);
    }

    public void addThema(Thema thema) {
        if (thema.getId() != null) {
            if (repository.containsKey(thema.getId())) {
                repository.update(thema.getId(), thema);
            }
        } else {
            thema.setId(repository.save(thema));
        }
    }

    public Thema getThema(String id) {
        if (repository.containsKey(id)) {
            return repository.get(id);
        } else {
            throw new NoSuchElementException("Thema with id " + id + "not found");
        }
    }

    public List<Thema> getAll() {
        return repository.getThemen();
    }

    public boolean allowedEdit(long profilID, Thema thema) {
        return profilID == thema.getProfilID();
    }

    public void deleteThema(String id, Integer profilID) {
        profilEditor.removeThema(profilID, id);
        repository.delete(id);
    }

    public void removeAllVoraussetzung (Voraussetzung voraussetzung) {
        List<Thema> list = repository.getThemen();
        list.forEach(e -> {
            e.removeVoraussetzung(voraussetzung);
            repository.update(e.getId(), e);
        });
    }

    public void updateVoraussetzungen(String id, Set<Voraussetzung> set) {
        Thema thema = getThema(id);
        thema.updateVoraussetzungen(set);
        repository.update(thema.getId(), thema);
    }

    public void addFachgebiet(String id, String fachgebiet) {
        Thema thema = getThema(id);
        thema.addFachgebiet(fachgebiet);
        repository.update(thema.getId(), thema);
    }

    public void removeFachgebiet(String id, String fachgebiet) {
        Thema thema = getThema(id);
        thema.removeFachgebiet(fachgebiet);
        repository.update(thema.getId(), thema);
    }

    public void addDatei(String id, DateiDTO datei) {
        Thema thema = getThema(id);
        thema.addDatei(datei);
        repository.update(id, thema);
    }

    public void removeDatei(String id, DateiDTO datei) {
        Thema thema = getThema(id);
        thema.removeDatei(datei);
        repository.update(id, thema);
    }

}
