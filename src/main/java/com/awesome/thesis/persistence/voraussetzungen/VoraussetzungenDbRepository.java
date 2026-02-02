package com.awesome.thesis.persistence.voraussetzungen;

import com.awesome.thesis.persistence.profiles.dtos.ProfilDto;
import com.awesome.thesis.persistence.voraussetzungen.dtos.VoraussetzungDto;
import java.util.Set;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

/**
 * Diese Klasse ist das technische Repository für das {@link VoraussetzungDto}.
 */
public interface VoraussetzungenDbRepository extends CrudRepository<VoraussetzungDto, String> {

  @Override
  @NonNull
  Set<VoraussetzungDto> findAll();

}
