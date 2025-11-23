package com.awesome.thesis.profiles.profil;

import java.util.List;

public class Profil {
    private String id;
    private String name;

    public Profil(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
