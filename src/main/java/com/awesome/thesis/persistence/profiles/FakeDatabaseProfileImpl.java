package com.awesome.thesis.persistence.profiles;

import com.awesome.thesis.logic.domain.model.profil.Kontakt;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.springframework.stereotype.Component;
import com.awesome.thesis.logic.domain.model.profil.Kontaktart;

import java.util.*;

@Component
class FakeDatabaseProfileImpl implements IDatabaseProfile {
    Map<Long, Profil> map = new HashMap<>();

    public FakeDatabaseProfileImpl() {
        Profil janik = new Profil(182077829,"Janik Daub");
        janik.addKontakt(new Kontakt("Email", "janik@mail.com", Kontaktart.EMAIL));
        update(182077829, janik);
        Profil ryota = new Profil(180645494, "Ryota Kariya");
        ryota.addKontakt(new Kontakt("Email", "ryota@mail.com", Kontaktart.EMAIL));
        update(180645494, ryota);
        Profil ole = new Profil(181595941, "Ole Marschik");
        ole.addKontakt(new Kontakt("Email", "ole@mail.com", Kontaktart.EMAIL));
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
