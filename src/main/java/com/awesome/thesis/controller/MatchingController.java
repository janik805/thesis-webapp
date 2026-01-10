package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
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

    @GetMapping("/matching")
    public String matching(Model model, @RequestParam(required = false) Set<String> interessen) {
        model.addAttribute("interessen", interessen);
        model.addAttribute("fachgebiete", fachgebieteEditor.getAll());
        model.addAttribute("profile", profilEditor.getAll());
        return "matching";
    }
}
