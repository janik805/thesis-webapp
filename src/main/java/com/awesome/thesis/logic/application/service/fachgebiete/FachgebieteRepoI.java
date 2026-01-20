package com.awesome.thesis.logic.application.service.fachgebiete;

import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import java.util.Set;

/**
 * Fachliches Repository für {@link Fachgebiet}.
 */
public interface FachgebieteRepoI {
  void add(Fachgebiet fachgebiet);
  
  void delete(String name);
  
  Set<Fachgebiet> getAll();
  
  boolean contains(String fachgebiet);
}
