package com.awesome.thesis.controller;

import com.awesome.thesis.profiles.ProfilEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfilController {
    @Autowired
    ProfilEditor editor;

    @GetMapping("/profil/{id}")
    public String getProfil(@PathVariable String id, Model model) {
        model.addAttribute("profil", editor.get(id));
        return "profil";
    }
}
