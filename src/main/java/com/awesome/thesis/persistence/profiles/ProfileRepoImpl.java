package com.awesome.thesis.persistence.profiles;

import com.awesome.thesis.logic.application.service.profiles.IProfileRepo;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileRepoImpl implements IProfileRepo {
    ProfileDBRepository dbRepository;

    public ProfileRepoImpl(ProfileDBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public Profil get(int id) {
        return dbRepository.findById(id);
    }

    @Override
    public boolean containsKey(int id) {
        return dbRepository.existsById(id);
    }

    @Override
    public void save(Profil profil) {
        dbRepository.insert(profil.getId(), profil.getName());
    }

    @Override
    public void update(Profil profil) {
        dbRepository.save(profil);
    }

    @Override
    public void delete(int id) {
        dbRepository.deleteById(id);
    }

    @Override
    public List<Profil> getAll() {
        return dbRepository.findAll();
    }
}
