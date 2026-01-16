package com.awesome.thesis.persistence.fachgebiete;

import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class FakeDatabaseFachgebiete implements IDatabaseFachgebiete {
    private final Map<String, Fachgebiet> map = new HashMap<String, Fachgebiet>();

    @Override
    public void add(String name, Fachgebiet fachgebiet) {
        map.put(name, fachgebiet);
    }

    @Override
    public void delete(String name) {
        map.remove(name);
    }

    @Override
    public Set<Fachgebiet> getAll() {
        return new HashSet<>(map.values());
    }

    @Override
    public boolean contains(String fachgebiet) {
        return map.containsKey(fachgebiet);
    }
}
