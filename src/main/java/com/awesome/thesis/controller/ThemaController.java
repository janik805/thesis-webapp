package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import com.awesome.thesis.logic.domain.model.fachgebiete.Fachgebiet;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class ThemaController {

    @Autowired
    ThemaEditor editor;

    @Autowired
    FachgebieteEditor fachEditor;

    @Autowired
    VoraussetzungenEditor vorEditor;


    @GetMapping("/themen")
    public String themenListe(Model model, @RequestParam(required = false) Set<String> interessen, @RequestParam(required = false) Set<String> voraussetzungen) {
        model.addAttribute("themenListe", editor.getFitting(voraussetzungen, interessen));
        model.addAttribute("fachgebiete", fachEditor.getAll());
        model.addAttribute("voraussetzungen", vorEditor.getAllString());
        model.addAttribute("interessen", interessen);
        model.addAttribute("eingabeVoraussetzungen", voraussetzungen);
        return "themen/themen";
    }

    @GetMapping("/thema/{id}")
    public String thema(@PathVariable("id") Integer id, Model model, OAuth2AuthenticationToken auth) {
        Thema thema = editor.getThema(id);
        Integer profilID = auth.getPrincipal().getAttribute("id");
        boolean canEdit = editor.allowedEdit(profilID, thema);
        model.addAttribute("thema", thema);
        model.addAttribute("canEdit", canEdit);
        model.addAttribute("profilID", thema.getProfilID());
        return "themen/thema";
    }
}
