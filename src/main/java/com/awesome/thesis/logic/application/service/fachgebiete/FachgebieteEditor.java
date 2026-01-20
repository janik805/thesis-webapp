package com.awesome.thesis.logic.application.service.fachgebiete;

import com.awesome.thesis.logic.application.service.profiles.ProfileRepoI;
import com.awesome.thesis.logic.application.service.themen.IThemaRepo;
import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import com.awesome.thesis.logic.domain.model.themen.ThemaFachgebiet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Die Klasse ist der Service von {@link Fachgebiet}.
 */
@Service
public class FachgebieteEditor {
  private final FachgebieteRepoI repo;
  private final ProfileRepoI profileRepo;
  private final IThemaRepo themaRepo;
  
  /**
   * Konstruktor für Konstruktor-Injection mit den nötigen Dependencies.
   *
   * @param repo        Repository von {@link Fachgebiet}
   * @param profileRepo Repository von Profil
   * @param themaRepo   Repository von Themen
   */
  public FachgebieteEditor(FachgebieteRepoI repo, ProfileRepoI profileRepo, IThemaRepo themaRepo) {
    this.repo = repo;
    this.profileRepo = profileRepo;
    this.themaRepo = themaRepo;
  }
  
  /**
   * Methode zum Hinzufügen von {@link Fachgebiet}.
   *
   * @param fachgebiet Name von Fachgebiet
   */
  public void add(String fachgebiet) {
    if (!repo.contains(fachgebiet)) {
      repo.add(new Fachgebiet(fachgebiet));
    }
  }
  
  /**
   * Methode um alle Fachgebiete zu erhalten.
   *
   * @return gibt alle gespeicherten Fachgebiete als Strings zurück
   */
  public Set<String> getAll() {
    return repo.getAll().stream()
        .map(Fachgebiet::getName)
        .collect(Collectors.toSet());
  }
  
  /**
   * Methode um Fachgebiet zu entfernen.
   *
   * @param fachgebiet Name des Fachgebietes
   */
  public void remove(String fachgebiet) {
    boolean profilUnused = profileRepo.getAll().stream()
        .noneMatch(p -> p.hasFachgebiet(fachgebiet));
    boolean themaUnused = themaRepo.getThemen().stream()
        .noneMatch(t -> t.hasFachgebiet(new ThemaFachgebiet(fachgebiet)));
    
    if (profilUnused && themaUnused) {
      repo.delete(fachgebiet);
    }
  }
}
