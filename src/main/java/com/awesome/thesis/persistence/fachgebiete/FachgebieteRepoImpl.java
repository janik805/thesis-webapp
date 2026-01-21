package com.awesome.thesis.persistence.fachgebiete;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteRepoI;
import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import com.awesome.thesis.persistence.fachgebiete.dto.FachgebietDto;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * Diese Klasse ist das fachliche Repository für das {@link Fachgebiet} Aggregat
 * und ist für das Mapping zwischen domain.model und DTOs der Datenbank.
 */
@Repository
public class FachgebieteRepoImpl implements FachgebieteRepoI {
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
      justification = "Spring Konstruktor Injection")
  private final FachgebieteDbRepository dbRepository;
  
  public FachgebieteRepoImpl(FachgebieteDbRepository dbRepository) {
    this.dbRepository = dbRepository;
  }
  
  
  @Override
  public void add(Fachgebiet fachgebiet) {
    dbRepository.insert(fachgebiet.getName());
  }
  
  @Override
  public void delete(String name) {
    dbRepository.deleteById(name);
  }
  
  @Override
  public Set<Fachgebiet> getAll() {
    return dbRepository.findAll().stream()
        .map(this::toFachgebiet)
        .collect(Collectors.toSet());
  }
  
  @Override
  public boolean contains(String fachgebiet) {
    return dbRepository.existsById(fachgebiet);
  }
  
  private Fachgebiet toFachgebiet(FachgebietDto dto) {
    return new Fachgebiet(dto.name());
  }
}
