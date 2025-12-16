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

    @GetMapping("createProfile")
    public String createProfile(Model model) {
        model.addAttribute("profile", editor.getAll());
        return "admin/profileAdmin";
    }

    @PostMapping("createProfile")
    public String createProfile(@Valid @ModelAttribute ProfilCreateDTO profil, BindingResult bindingResult) {
        if  (bindingResult.hasErrors()) {
            return "admin/profileAdmin";
        }
        editor.create(profil.id(), profil.name());
        return "redirect:/admin/createProfile";
    }
}
