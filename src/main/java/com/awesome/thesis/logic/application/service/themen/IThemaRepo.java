package com.awesome.thesis.logic.application.service.themen;

import com.awesome.thesis.logic.domain.model.themen.Thema;

import java.util.List;

public interface IThemaRepo {
    void addThema(Thema thema);
    void removeThema (String id);
    List<Thema> getThemen();
    Thema getThema(String titel);
}
