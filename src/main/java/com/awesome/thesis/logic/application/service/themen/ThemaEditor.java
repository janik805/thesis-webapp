package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.themen.Thema;

import java.util.NoSuchElementException;

public class ThemaEditor {
    IThemaRepo repository;

    public ThemaEditor(IThemaRepo repository) {
        this.repository = repository;
    }

    public void addLink(String id, Link link) {
        Thema thema = getThema(id);
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
        if(!titel.isEmpty()) {
            thema.setTitel(titel);
            repository.update(id, thema);
        } else {
            throw new IllegalArgumentException("Titel should not be empty");
        }
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




}
