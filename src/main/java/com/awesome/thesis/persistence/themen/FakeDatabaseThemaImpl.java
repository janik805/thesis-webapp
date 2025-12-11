package com.awesome.thesis.persistence.themen;
import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FakeDatabaseThemaImpl implements IDatabaseThema{
    Map<String, Thema> map = new HashMap<>();

    public FakeDatabaseThemaImpl() {
        Thema thema = new Thema("Programmierpraktikum 2");
        thema.addUrl(new Link("https://www.google.com/", "Google als Beispiel"));
        thema.addUrl(new Link("https://www.youtube.com/", "Youtube als Beispiel"));
        thema.setId("propra");
        update("propra", thema);
    }
    public Thema get(String id) {
        return map.get(id);
    }

    public String save(Thema thema) {
        String id = UUID.randomUUID().toString();
        map.put(id, thema);
        return id;
    }

    public boolean containsKey(String id) {
        return map.containsKey(id);
    }

    public void update(String id, Thema thema) {
        map.put(id, thema);
    }

    public void delete(String id) {
        map.remove(id);
    }

    public List<Thema> getAll() {
        return new ArrayList<>(map.values());
    }

}
