package com.awesome.thesis.logic.application.service.fachgebiete;

import com.awesome.thesis.logic.application.service.profiles.ProfileRepoI;
import com.awesome.thesis.logic.application.service.themen.IThemaRepo;
import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import com.awesome.thesis.logic.domain.model.themen.ThemaFachgebiet;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FachgebieteEditor {
    private final IFachgebieteRepo repo;
    private final ProfileRepoI profileRepo;
    private final IThemaRepo themaRepo;

    public FachgebieteEditor(IFachgebieteRepo repo, ProfileRepoI profileRepo, IThemaRepo themaRepo) {
        this.repo = repo;
        this.profileRepo = profileRepo;
        this.themaRepo = themaRepo;
    }

    public void add(String fachgebiet) {
        if (!repo.contains(fachgebiet)) {
            repo.add(new Fachgebiet(fachgebiet));
        }
    }

    public Set<String> getAll() {
        return repo.getAll().stream()
                .map(Fachgebiet::getName)
                .collect(Collectors.toSet());
    }

    public void remove(String fachgebiet) {
        boolean profilUnused = profileRepo.getAll().stream().noneMatch(p -> p.hasFachgebiet(fachgebiet));
        boolean themaUnused = themaRepo.getThemen().stream().noneMatch(t -> t.hasFachgebiet(new ThemaFachgebiet(fachgebiet)));

        if (profilUnused && themaUnused) {
            repo.delete(fachgebiet);
        }
    }
}
