package com.awesome.thesis.controller.admin;

import com.awesome.thesis.controller.dto.ProfilCreateDTO;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Profil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminProfilCreator {
    @Autowired
    ProfilEditor editor;

    @GetMapping()
    public String createProfile(Model model) {
        model.addAttribute("profil", new ProfilCreateDTO(null, ""));
        model.addAttribute("profile", editor.getAll());
        return "admin/profileAdmin";
    }

    @PostMapping()
    public String createProfile(@Valid @ModelAttribute("profil") ProfilCreateDTO profil, BindingResult bindingResult, Model model) {
        if  (bindingResult.hasErrors()) {
            model.addAttribute("profile", editor.getAll());
            return "admin/profileAdmin";
        }
        editor.create(profil.id(), profil.name());
        return "redirect:/admin/createProfile";
    }
}
