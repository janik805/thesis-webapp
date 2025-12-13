package com.awesome.thesis.controller;

import com.awesome.thesis.controller.dto.EmailKontaktDTO;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Kontakt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminController {
    @Autowired
    ProfilEditor editor;

    @GetMapping("profilEdit/{id}")
    public String profilEdit(@PathVariable String id, Model model) {
        model.addAttribute("profil", editor.get(id));
        model.addAttribute("email", new EmailKontaktDTO("", ""));
        return "admin/profilEdit";
    }

    @PostMapping("profilEdit/{id}")
    public String profilEdit(@PathVariable String id, @ModelAttribute("name") String name) {
        editor.editName(id, name);
        return "redirect:/admin/profilEdit/" + id;
    }

    @PostMapping("profilEdit/{id}/deleteKontakt")
    public String deleteKontakt(@PathVariable String id, @ModelAttribute Kontakt kontakt) {
        editor.removeKontakt(id, kontakt);
        return "redirect:/admin/profilEdit/" + id;
    }

    @PostMapping("profilEdit/{id}/addEmail")
    public String addEmail(@PathVariable String id, @Valid @ModelAttribute("email") EmailKontaktDTO email, BindingResult result, Model model) {
        if  (result.hasErrors()) {
            model.addAttribute("profil", editor.get(id));
            return "admin/profilEdit";
        }
        editor.addEmail(id, email.label(), email.wert());
        return "redirect:/admin/profilEdit/" + id;
    }
}
