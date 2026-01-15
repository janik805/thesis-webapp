package com.awesome.thesis.persistence.fachgebiete;

import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import org.springframework.data.repository.CrudRepository;

public interface FachgebieteDBRepository extends CrudRepository<Fachgebiet, String> {
}
