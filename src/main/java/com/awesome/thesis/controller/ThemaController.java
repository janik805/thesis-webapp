package com.awesome.thesis.controller;

import com.awesome.thesis.controller.dto.ThemaInfoDTO;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ThemaController {

    @Autowired
    ThemaEditor editor;

    @GetMapping("/themen")
    public String themenListe(Model model) {
        model.addAttribute("themenListe", editor.getAll());
        return "themen/themen";
    }

    @GetMapping("/thema/{id}")
    public String thema(@PathVariable("id") String id, Model model, OAuth2AuthenticationToken auth) {
        Thema thema = editor.getThema(id);
        Integer profilID = auth.getPrincipal().getAttribute("id");
        boolean canEdit = editor.allowedEdit(profilID, thema);
        model.addAttribute("thema", thema);
        model.addAttribute("canEdit", canEdit);
        return "themen/thema";
    }

    @GetMapping("/themen/{id}")
    public String themaInListe(@PathVariable("id") String id, Model model, OAuth2AuthenticationToken auth) {
        Thema thema = editor.getThema(id);
        Integer profilID = auth.getPrincipal().getAttribute("id");
        boolean canEdit = editor.allowedEdit(profilID, thema);
        model.addAttribute("thema", thema);
        model.addAttribute("canEdit", canEdit);
        model.addAttribute("id", thema.getProfilID());
        return "themen/themaInListe";
    }
}
