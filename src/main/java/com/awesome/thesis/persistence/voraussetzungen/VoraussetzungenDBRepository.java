package com.awesome.thesis.persistence.voraussetzungen;

import com.awesome.thesis.persistence.voraussetzungen.dtos.VoraussetzungDTO;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface VoraussetzungenDBRepository extends CrudRepository<VoraussetzungDTO, String> {

    Set<VoraussetzungDTO> findAll();

    @Modifying
    @Query("insert into voraussetzung values (:name)")
    void insert(@Param("name") String name);

}
