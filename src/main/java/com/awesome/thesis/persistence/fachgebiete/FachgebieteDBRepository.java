package com.awesome.thesis.persistence.fachgebiete;

import com.awesome.thesis.persistence.fachgebiete.dto.FachgebietDTO;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Set;

public interface FachgebieteDBRepository extends CrudRepository<FachgebietDTO, String> {
    @NonNull
    Set<FachgebietDTO> findAll();

    @Modifying
    @Query("insert into fachgebiet values (:name)")
    void insert(@Param("name") String name);
}
