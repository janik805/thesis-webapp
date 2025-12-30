package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.controller.dto.ThemaInfoDTO;
import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ThemaEditor {
    IThemaRepo repository;

    public ThemaEditor(IThemaRepo repository) {
        this.repository = repository;
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

    public void editTitel(String id, String titel) {
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




}
