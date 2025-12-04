package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.domain.model.themen.Thema;

import java.util.List;

public interface IThemaRepository {
    void addThema(Thema thema);
    void removeThema (Thema thema);
    List<Thema> getThemen();
    Thema getThema(String titel);
}
