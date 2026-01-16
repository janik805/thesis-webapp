package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.application.dto.DateiDTO;
import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import com.awesome.thesis.logic.domain.model.themen.*;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ThemaEditor {
    IThemaRepo repository;

    private final ProfilEditor profilEditor;

    private final FachgebieteEditor fachEditor;

    public ThemaEditor(IThemaRepo repository, ProfilEditor profilEditor, FachgebieteEditor fachEditor) {
        this.repository = repository;
        this.profilEditor = profilEditor;
        this.fachEditor = fachEditor;
    }

    public void addLink(String id, String url, String urlBeschreibung) {
        Thema thema = getThema(id);
        ThemaLink link = new ThemaLink(url, urlBeschreibung);
        thema.addUrl(link);
        repository.update(id,thema);
    }

    public void removeLink(String id, ThemaLink link) {
        Thema thema = getThema(id);
        thema.removeUrl(link);
        repository.update(id, thema);
    }

    public void editTitel(int profilID, String id, String titel) {
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

    public void addThema(Thema thema, int profilID) {
        if (thema.getId() != null) {
            if (repository.containsKey(thema.getId())) {
                repository.update(thema.getId(), thema);
            }
        } else {
            thema.setId(repository.save(thema));
        }
        profilEditor.addThema(profilID, thema.getId(), thema.getTitel());
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

    public void removeAllVoraussetzung(Voraussetzung v) {
        List<Thema> themen = repository.getThemen();
        themen.forEach(t -> {
            t.removeVoraussetzung(
                    new ThemaVoraussetzung(v.voraussetzung())
            );
            repository.update(t.getId(), t);
        });
    }

    public void updateVoraussetzungen(String id, Set<String> voraussetzungen) {
            Set<ThemaVoraussetzung> safeVoraussetzungen = mapToThemaVoraussetzung(voraussetzungen);
            Thema thema = getThema(id);
            thema.updateVoraussetzungen(safeVoraussetzungen);
            repository.update(thema.getId(), thema);
    }

    public void addFachgebiet(String id, String fachgebiet) {
        Thema thema = getThema(id);
        thema.addFachgebiet(new ThemaFachgebiet(fachgebiet));
        fachEditor.add(fachgebiet);
        repository.update(thema.getId(), thema);
    }

    public void removeFachgebiet(String id, String fachgebiet) {
        Thema thema = getThema(id);
        thema.removeFachgebiet(new ThemaFachgebiet(fachgebiet));
        fachEditor.remove(fachgebiet);
        repository.update(thema.getId(), thema);
    }

    public void addDatei(String id, ThemaDateiValue datei) {
        Thema thema = getThema(id);
        thema.addDatei(datei);
        repository.update(id, thema);
    }

    public void removeDatei(String id, ThemaDateiValue datei) {
        Thema thema = getThema(id);
        thema.removeDatei(datei);
        repository.update(id, thema);
    }

    public Set<ThemaVoraussetzung> mapToThemaVoraussetzung(Set<String> voraussetzungen) {
        if(!(voraussetzungen == null)) {
            return voraussetzungen.stream()
                    .map(ThemaVoraussetzung::new)
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

    public List<Thema> getFitting(Set<String> voraussetzungen, Set<String> interessen) {
            Set<ThemaFachgebiet> themaFachgebiet = mapToThemaFachgebiet(interessen);
            Set<ThemaVoraussetzung> themaVoraussetzungen = mapToThemaVoraussetzung(voraussetzungen);
            return getAll().stream()
                    .filter(e -> e.fitsRequirements(themaVoraussetzungen, themaFachgebiet))
                    .toList();
    }

    public List<Thema> sortRang(Set<String> voraussetzungen, Set<String> interessen) {
            Set<ThemaVoraussetzung> themaVoraussetzungen = mapToThemaVoraussetzung(voraussetzungen);
            Set<ThemaFachgebiet> themaFachgebiet = mapToThemaFachgebiet(interessen);
            return getAll().stream()
                    .filter(e -> e.calcRang(themaVoraussetzungen, themaFachgebiet) != -1)
                    .sorted(Comparator.comparingLong((Thema thema) -> thema.calcRang(themaVoraussetzungen, themaFachgebiet)).reversed())
                    .toList();
    }

    private Set<ThemaFachgebiet> mapToThemaFachgebiet(Set<String> interessen) {
        if(!(interessen == null)) {
            return interessen.stream().map(ThemaFachgebiet::new).collect(Collectors.toSet());
        } else {
            return Set.of();
        }
    }

    public Set<Voraussetzung> getVoraussetzungen(String id) {
        Thema thema = getThema(id);
        Set<ThemaVoraussetzung> voraussetzungen = thema.getVoraussetzungen();
        return voraussetzungen.stream().map(e -> new Voraussetzung(e.voraussetzung())).collect(Collectors.toSet());
    }
}
