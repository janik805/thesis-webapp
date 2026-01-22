package com.awesome.thesis.logic.application.service.profiles;

import com.awesome.thesis.logic.domain.model.profil.Profil;
import java.util.List;

/**
 * Interface für fachliches Repository von {@link Profil}.
 */
public interface ProfileRepoI {
  Profil get(int id);
  
  boolean containsKey(int id);
  
  void save(Profil profil);
  
  void delete(int id);
  
  List<Profil> getAll();
}
