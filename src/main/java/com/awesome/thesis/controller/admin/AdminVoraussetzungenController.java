package com.awesome.thesis.controller.admin;

import com.awesome.thesis.controller.dto.VoraussetzungDto;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import com.awesome.thesis.logic.domain.model.voraussetzungen.Voraussetzung;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
@Controller
public class AdminVoraussetzungenController {

    @Autowired
    VoraussetzungenEditor vorEditor;

    @Autowired
    ThemaEditor themaEditor;

    @GetMapping("/module/edit")
    public String editVoraussetzung(Model model) {
        model.addAttribute("vorDTO", new VoraussetzungDto(""));
        model.addAttribute("vorListe", vorEditor.getAll());
        return "admin/voraussetzungen";
    }

    @PostMapping("/addVoraussetzung")
    public String addVoraussetzung(@Valid VoraussetzungDto vorDTO, BindingResult result) {
        if(result.hasErrors()) {
            return "redirect:/admin/module/edit";
        }
        vorEditor.add(new Voraussetzung(vorDTO.voraussetzung()));
        return "redirect:/admin/module/edit";
    }

    @PostMapping("/checkRemoveVoraussetzung")
    public String checkRemoveVoraussetzung(@RequestParam String voraussetzung, Model model) {
        model.addAttribute("voraussetzung", voraussetzung);
        return "themen/confirmVoraussetzungDeletion";
    }

    @PostMapping("/removeVoraussetzung")
    public String removeVoraussetzung(@RequestParam String voraussetzung) {
        vorEditor.remove(new Voraussetzung(voraussetzung));
        themaEditor.removeAllVoraussetzung(new Voraussetzung(voraussetzung));
        return "redirect:/admin/module/edit";
    }

}
