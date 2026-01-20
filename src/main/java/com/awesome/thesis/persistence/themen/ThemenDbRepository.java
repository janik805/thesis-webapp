package com.awesome.thesis.persistence.themen;

import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.persistence.themen.dtos.ThemaDto;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Diese Klasse ist das technische Repository für das {@link ThemaDto}.
 */
public interface ThemenDbRepository extends CrudRepository<ThemaDto, Integer> {

  void save(Thema thema);

  ThemaDto findById(int id);

  List<ThemaDto> findAll();

}
