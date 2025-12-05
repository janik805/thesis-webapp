package com.awesome.thesis.persistence.themen;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.application.service.themen.IThemaRepo;
import java.util.List;
import java.util.NoSuchElementException;

public class ThemaRepoImpl implements IThemaRepo {
    IDatabaseThema database;

    public ThemaRepoImpl(IDatabaseThema database) {
        this.database = database;
    }

    @Override
    public void addThema(Thema thema) {
        database.save(thema);
    }

    @Override
    public void removeThema(String id) {
        database.delete(id);
    }

    @Override
    public List<Thema> getThemen() {
        return database.getAll();
    }

    @Override
    public Thema getThema(String id) {
        if (database.containsKey(id)) {
            return database.get(id);
        } else {
            throw new NoSuchElementException("Thema mit id" + id + "nicht gefunden");
        }
    }
}
