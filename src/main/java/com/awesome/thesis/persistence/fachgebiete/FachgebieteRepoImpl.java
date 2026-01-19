package com.awesome.thesis.persistence.fachgebiete;

import com.awesome.thesis.logic.application.service.fachgebiete.IFachgebieteRepo;
import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import com.awesome.thesis.persistence.fachgebiete.dto.FachgebietDTO;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class FachgebieteRepoImpl implements IFachgebieteRepo {
    private final FachgebieteDBRepository dbRepository;

    public FachgebieteRepoImpl(FachgebieteDBRepository dbRepository) {
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

    private Fachgebiet toFachgebiet(FachgebietDTO dto) {
        return new Fachgebiet(dto.name());
    }
}
