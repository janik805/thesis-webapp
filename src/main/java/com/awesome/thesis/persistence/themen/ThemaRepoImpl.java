package com.awesome.thesis.persistence.themen;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.application.service.themen.IThemaRepo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ThemaRepoImpl implements IThemaRepo {
    IDatabaseThema database;

    public ThemaRepoImpl(IDatabaseThema database) {
        this.database = database;
    }

    @Override
    public String save(Thema thema) {
        return database.save(thema);
    }

    public boolean containsKey(String id) {
        return database.containsKey(id);
    }

    @Override
    public void delete(String id) {
        database.delete(id);
    }

    @Override
    public List<Thema> getThemen() {
        return database.getAll();
    }

    @Override
    public Thema get(String id) {
        return database.get(id);
    }

    @Override
    public void update(String id, Thema thema) {
        database.update(id, thema);
    }
}
