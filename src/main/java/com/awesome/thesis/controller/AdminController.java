package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("profilEdit/{id}")
    public String profilEdit(@PathVariable String id, @ModelAttribute("name") String name) {
        editor.editName(id, name);
        return "redirect:/admin/profilEdit/" + id;
    }
}
