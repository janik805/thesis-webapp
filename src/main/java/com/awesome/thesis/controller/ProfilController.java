package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfilController {
    @Autowired
    ProfilEditor profilEditor;

    @Autowired
    FachgebieteEditor fachgebieteEditor;

    @GetMapping("/betreuende")
    public String getProfil(Model model) {
        model.addAttribute("fachgebiete", fachgebieteEditor.getAll());
        model.addAttribute("profile", profilEditor.getAll());
        return "profiles/profile";
    }

    @GetMapping("/betreuende/{id}")
    public String getProfil(@PathVariable Long id, Model model) {
        model.addAttribute("profil", profilEditor.get(id));
        return "profiles/profil";
    }
}
