package com.awesome.thesis.persistence.profiles;

import com.awesome.thesis.logic.domain.model.profil.ProfilKontakt;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.springframework.stereotype.Component;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontaktart;

import java.util.*;

@Component
class FakeDatabaseProfileImpl implements IDatabaseProfile {
    Map<Long, Profil> map = new HashMap<>();

    public FakeDatabaseProfileImpl() {
        Profil janik = new Profil(182077829,"Janik Daub");
        janik.addEmail("Email", "janik@mail.com");
        update(182077829, janik);
        Profil ryota = new Profil(180645494, "Ryota Kariya");
        ryota.addEmail("Email", "ryota@mail.com");
        update(180645494, ryota);
        Profil ole = new Profil(181595941, "Ole Marschik");
        ole.addEmail("Email", "ole@mail.com");
        update(181595941, ole);
    }

    public Profil get(long id) {
        return map.get(id);
    }

    public void save(long id, Profil profil) {
        map.put(id, profil);
    }

    public boolean containsKey(long id) {
        return map.containsKey(id);
    }

    public void update(long id, Profil profil) {
        map.put(id, profil);
    }

    public void delete(long id) {
        map.remove(id);
    }

    public List<Profil> getAll() {
        return new ArrayList<>(map.values());
    }
}
