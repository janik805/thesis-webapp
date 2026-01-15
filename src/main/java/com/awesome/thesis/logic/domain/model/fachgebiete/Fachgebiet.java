package com.awesome.thesis.logic.domain.model.fachgebiete;

import com.awesome.thesis.annotations.AggregateRoot;
import org.springframework.data.annotation.Id;

@AggregateRoot
public class Fachgebiet {
    @Id
    private final String name;

    public Fachgebiet (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
