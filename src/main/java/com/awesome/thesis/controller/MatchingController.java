package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class MatchingController {
    @Autowired
    ProfilEditor profilEditor;

    @Autowired
    FachgebieteEditor fachgebieteEditor;

    @Autowired
    VoraussetzungenEditor vorEditor;

    @Autowired
    ThemaEditor themaEditor;

    @GetMapping("/matching")
    public String matching(Model model, @RequestParam(required = false) Set<String> interessen, @RequestParam(required = false) Set<String> voraussetzungen) {
        Set<Voraussetzung> actualVoraussetzungen = vorEditor.mapToVoraussetzung(voraussetzungen);
        model.addAttribute("fachgebiete", fachgebieteEditor.getAll());
        model.addAttribute("voraussetzungen", vorEditor.getAll());
        model.addAttribute("themenListe", themaEditor.sortRang(actualVoraussetzungen, interessen));
        model.addAttribute("profile", profilEditor.getMatching(interessen));
        model.addAttribute("interessen", interessen);
        model.addAttribute("eingabeVoraussetzungen", actualVoraussetzungen);
        return "matching";
    }
}
