package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ProfilEditor editor;

    @GetMapping("profilEdit/{id}")
    public String profilEdit(@PathVariable String id, Model model) {
        model.addAttribute("profil", editor.get(id));
        return "admin/profilEdit";
    }
}
