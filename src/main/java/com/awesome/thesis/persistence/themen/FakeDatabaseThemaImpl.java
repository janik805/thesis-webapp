package com.awesome.thesis.persistence.themen;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaLink;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FakeDatabaseThemaImpl implements IDatabaseThema{
    Map<Integer, Thema> map = new HashMap<>();

    public FakeDatabaseThemaImpl() {
        Thema thema = new Thema("Programmierpraktikum 2", 180645494);
        thema.addUrl(new ThemaLink("https://www.google.com/", "Google als Beispiel"));
        thema.addUrl(new ThemaLink("https://www.youtube.com/", "Youtube als Beispiel"));
        thema.setBeschreibung("Hier wird viel programmiert! Lernen durch Handeln.");
        thema.setId(3);
        update(3, thema);
    }
    public Thema get(Integer id) {
        return map.get(id);
    }

    public Integer save(Thema thema) {
        Random random = new Random();
        Integer id = random.nextInt(1000);
        map.put(id, thema);
        return id;
    }

    public boolean containsKey(Integer id) {
        return map.containsKey(id);
    }

    public void update(Integer id, Thema thema) {
        map.put(id, thema);
    }

    public void delete(Integer id) {
        map.remove(id);
    }

    public List<Thema> getAll() {
        return new ArrayList<>(map.values());
    }

}
