package com.awesome.thesis.controller.admin;
import com.awesome.thesis.controller.dto.ThemaInfoDTO;
import com.awesome.thesis.controller.dto.LinkDTO;
import com.awesome.thesis.logic.application.dto.ThemaDTO;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.Voraussetzung;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@Secured("ROLE_BETREUENDE")
public class ThemaEditorController {

    @Autowired
    ThemaEditor themaEditor;

    @Autowired
    ProfilEditor profilEditor;

    @Autowired
    VoraussetzungenEditor vorEditor;

    @GetMapping("/themaEdit/{id}")
    public String editThema(@PathVariable("id")String id, Model model, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (themaEditor.allowedEdit(profilID, thema)) {
            ThemaInfoDTO info = new ThemaInfoDTO(thema.getTitel(), thema.getBeschreibung());
            model.addAttribute("themaLinkDTO", new LinkDTO("", ""));
            model.addAttribute("themaInfoDTO", info);
            model.addAttribute("thema", themaEditor.getThema(id));
            model.addAttribute("profilID", profilID);
            model.addAttribute("themaVoraussetzungen", thema.getVoraussetzungen());
            model.addAttribute("voraussetzungen", vorEditor.getAll());
            return "admin/themaEdit";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("themaEdit/{id}/editInfo")
    public String editThemaInfo(@PathVariable String id,@Valid @ModelAttribute("themaInfoDTO") ThemaInfoDTO themaInfoDTO, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if(result.hasErrors()) {
            model.addAttribute("themaLinkDTO", new LinkDTO("", ""));
            model.addAttribute("thema", thema);
            return "admin/themaEdit";
        }
        if (themaEditor.allowedEdit(profilID, thema)) {
            profilEditor.removeThema(profilID, new ThemaDTO(id, thema.getTitel()));
            profilEditor.addThema(profilID, new ThemaDTO(id, themaInfoDTO.titel()));
            themaEditor.editTitel(id, themaInfoDTO.titel());
            themaEditor.editBeschreibung(id, themaInfoDTO.beschreibung());
            return "redirect:/themaEdit/" + id;
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/themaEdit/{id}/editLink")
    public String editThemaLink(@PathVariable String id, @Valid @ModelAttribute("themaLinkDTO") LinkDTO dto, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (result.hasErrors()){
            ThemaInfoDTO info = new ThemaInfoDTO(thema.getTitel(), thema.getBeschreibung());
            model.addAttribute("themaInfoDTO", info);
            model.addAttribute("thema", themaEditor.getThema(id));
            return "admin/themaEdit";
        }
        if (themaEditor.allowedEdit(profilID, thema)) {
            themaEditor.addLink(id, dto.url(), dto.urlBeschreibung());
            return "redirect:/themaEdit/" + id;
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/themaEdit/{id}/deleteLink")
    public String deleteLink(@ModelAttribute Link link, @PathVariable String id, @ModelAttribute("themaLinkDTO") LinkDTO dto, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        if (themaEditor.allowedEdit(profilID, themaEditor.getThema(id))) {
            themaEditor.removeLink(id, link);
            return "redirect:/themaEdit/" + id;
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/themaAnsicht/{id}")
    public String returnToThema(@PathVariable String id) {
        return "redirect:/thema/" + id;
    }

    @PostMapping("/themaEdit/{id}/addVoraussetzung")
    public String addVoraussetzung(@RequestParam Set<String> voraussetzungen, @PathVariable String id, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (themaEditor.allowedEdit(profilID, thema)) {
            voraussetzungen.stream().forEach(e -> themaEditor.addVoraussetzung(id, new Voraussetzung(e)));
            return "redirect:/themaEdit/" + id;
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/themaEdit/{id}/removeVoraussetzung")
    public String removeVoraussetzung(@RequestParam String voraussetzung, @PathVariable String id, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (themaEditor.allowedEdit(profilID, thema)) {
            themaEditor.removeVoraussetzung(id, new Voraussetzung(voraussetzung));
            return "redirect:/themaEdit/" + id;
        } else {
            return "redirect:/";
        }
    }
}
