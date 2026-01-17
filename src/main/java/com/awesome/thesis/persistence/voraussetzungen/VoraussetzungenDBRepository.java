package com.awesome.thesis.persistence.voraussetzungen;

import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface VoraussetzungenDBRepository extends CrudRepository<Voraussetzung, String> {

    Set<Voraussetzung> findAll();

    @Modifying
    @Query("insert into voraussetzung values (:name)")
    void insert(@Param("name") String name);

}
