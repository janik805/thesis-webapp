package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ThemaController {

    @Autowired
    ThemaEditor thema;

    @GetMapping("/themenListe")
    public String themenListe(Model model) {
        model.addAttribute("themenListe", thema.getAll());
        return "themen/themenListe";
    }

    @GetMapping("/thema/{id}")
    public String thema(@PathVariable("id") String id, Model model) {
        model.addAttribute("thema", thema.getThema(id));
        return "themen/thema";
    }
}
