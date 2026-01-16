package com.awesome.thesis.logic.application.service.fachgebiete;

import com.awesome.thesis.logic.application.service.profiles.IProfileRepo;
import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FachgebieteEditor {
    private final IFachgebieteRepo repo;
    private final IProfileRepo profileRepo;

    public FachgebieteEditor(IFachgebieteRepo repo, IProfileRepo profileRepo) {
        this.repo = repo;
        this.profileRepo = profileRepo;
    }

    public void add(String fachgebiet) {
        if (!repo.contains(fachgebiet)) {
            repo.add(fachgebiet, new Fachgebiet(fachgebiet));
        }
    }

    public Set<String> getAll() {
        return repo.getAll().stream()
                .map(Fachgebiet::getName)
                .collect(Collectors.toSet());
    }

    public void remove(String fachgebiet) {
        boolean unused = profileRepo.getAll().stream()
                .noneMatch(p -> p.hasFachgebiet(fachgebiet));

        if (unused) {
            repo.delete(fachgebiet);
        }
    }
}
