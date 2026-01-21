package com.awesome.thesis.persistence.themen;

import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.persistence.themen.dtos.ThemaDto;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

/**
 * Diese Klasse ist das technische Repository für das {@link ThemaDto}.
 */
public interface ThemenDbRepository extends CrudRepository<ThemaDto, Integer> {

  /**
   * Speichert ein Thema in der Datenbank.
   *
   * @param thema Das Thema, das gespeichert werden soll.
   */
  void save(Thema thema);

  /**
   * Sucht ein ThemaDto nach seiner Id.
   *
   * @param id Die Id, nachdem das ThemaDto gesucht wird.
   * @return Ein ThemaDto, falls gefunden.
   */
  ThemaDto findById(int id);

  /**
   * Sucht alle ThemaDto's in der Datenbank.
   *
   * @return Eine Liste von allen ThemaDto's in der Datenbank.
   */
  @NonNull
  List<ThemaDto> findAll();

}
