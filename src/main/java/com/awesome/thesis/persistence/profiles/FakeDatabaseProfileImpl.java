package com.awesome.thesis.persistence.profiles;

import com.awesome.thesis.logic.domain.model.profil.Kontakt;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.springframework.stereotype.Component;
import com.awesome.thesis.logic.domain.model.profil.Kontaktart;

import java.util.*;

@Component
class FakeDatabaseProfileImpl implements IDatabaseProfile {
    Map<String, Profil> map = new HashMap<>();

    public FakeDatabaseProfileImpl() {
        Profil profil1 = new Profil("max");
        profil1.addKontakt(new Kontakt("Email", "max@mail.com", Kontaktart.EMAIL));
        profil1.setId(save(profil1));
    }

    public Profil get(String key) {
        return map.get(key);
    }

    public String save(Profil profil) {
        String id = UUID.randomUUID().toString();
        System.out.println("Saving " + id);
        map.put(id, profil);
        return id;
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public void update(String key, Profil profil) {
        map.put(key, profil);
    }

    public void delete(String key) {
        map.remove(key);
    }

    public List<Profil> getAll() {
        return new ArrayList<>(map.values());
    }
}
