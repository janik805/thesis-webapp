package com.awesome.thesis.persistence.fachgebiete;

import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Set;

public interface FachgebieteDBRepository extends CrudRepository<Fachgebiet, String> {
    @NonNull
    Set<Fachgebiet> findAll();

    @Modifying
    @Query("insert into fachgebiet values (:name)")
    void save(@Param("name") String name);
}
