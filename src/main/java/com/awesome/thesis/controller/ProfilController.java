package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class ProfilController {
    @Autowired
    ProfilEditor profilEditor;

    @Autowired
    FachgebieteEditor fachgebieteEditor;

    @GetMapping("/betreuende")
    public String getProfile(@RequestParam(required = false) Set<String> interessen, Model model) {
        model.addAttribute("interessen", interessen);
        model.addAttribute("fachgebiete", fachgebieteEditor.getAll());
        model.addAttribute("profile", profilEditor.getFitting(interessen));
        return "profiles/profile";
    }

    @GetMapping("/betreuende/{id}")
    public String getProfil(@PathVariable Long id, Model model) {
        model.addAttribute("profil", profilEditor.get(id));
        return "profiles/profil";
    }
}
