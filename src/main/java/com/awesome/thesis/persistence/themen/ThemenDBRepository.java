package com.awesome.thesis.persistence.themen;

import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ThemenDBRepository extends CrudRepository<Thema, Integer> {
    Thema findById(int id);
    List<Thema> findAll();

}
