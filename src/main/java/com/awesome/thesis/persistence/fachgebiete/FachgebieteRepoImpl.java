package com.awesome.thesis.persistence.fachgebiete;

import com.awesome.thesis.logic.application.service.fachgebiete.IFachgebieteRepo;
import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class FachgebieteRepoImpl implements IFachgebieteRepo {
    private final IDatabaseFachgebiete database;

    public FachgebieteRepoImpl(IDatabaseFachgebiete database) {
        this.database = database;
    }


    @Override
    public void add(String name, Fachgebiet fachgebiet) {
        database.add(name, fachgebiet);
    }

    @Override
    public void delete(String name) {
        database.delete(name);
    }

    @Override
    public Set<Fachgebiet> getAll() {
        return database.getAll();
    }

    @Override
    public boolean contains(String fachgebiet) {
        database.contains(fachgebiet);
    }
}
