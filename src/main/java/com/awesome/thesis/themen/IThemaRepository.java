package com.awesome.thesis.themen;

import java.util.List;

public interface IThemaRepository {
    void addThema(Thema thema);
    void removeThema (Thema thema);
    List<Thema> getThemen();
    Thema getThema(String titel);
}
