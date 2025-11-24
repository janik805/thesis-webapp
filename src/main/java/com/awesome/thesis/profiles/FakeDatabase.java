package com.awesome.thesis.profiles;

import com.awesome.thesis.profiles.profil.Profil;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
class FakeDatabase implements Database {
    Map<String, Profil> map = new HashMap<>();

    public Profil get(String key) {
        return map.get(key);
    }

    public String save(Profil profil) {
        String id = UUID.randomUUID().toString();
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
