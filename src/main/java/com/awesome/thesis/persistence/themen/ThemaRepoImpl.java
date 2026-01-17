package com.awesome.thesis.persistence.themen;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.application.service.themen.IThemaRepo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ThemaRepoImpl implements IThemaRepo {
    ThemenDBRepository database;

    public ThemaRepoImpl(ThemenDBRepository database) {
        this.database = database;
    }

    @Override
    public void save(Thema thema) {
        database.save(thema);
    }

    public boolean containsKey(Integer id) {
        return database.existsById(id);
    }

    @Override
    public void delete(Integer id) {
        database.deleteById(id);
    }

    @Override
    public List<Thema> getThemen() {
        return database.findAll();
    }

    @Override
    public Thema get(int id) { return database.findById(id);
    }

    @Override
    public void update(Integer id, Thema thema) {
        database.save(thema);
    }
}
