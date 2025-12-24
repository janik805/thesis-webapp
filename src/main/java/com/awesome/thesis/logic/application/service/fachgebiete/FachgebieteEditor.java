package com.awesome.thesis.logic.application.service.fachgebiete;

import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class FachgebieteEditor {
    private final IFachgebieteRepo repo;

    @Autowired
    ProfilEditor profilEditor;

    public FachgebieteEditor(IFachgebieteRepo repo) {
        this.repo = repo;
    }

    public void add(String fachgebiet) {
        repo.add(fachgebiet);
    }

    public Set<String> getAll() {
        return repo.getAll();
    }

    public void remove(String fachgebiet) {
        boolean unused = profilEditor.getAll().stream()
                .noneMatch(p -> p.hasFachgebiet(fachgebiet));

        if (unused) {
            repo.delete(fachgebiet);
        }
    }
}
