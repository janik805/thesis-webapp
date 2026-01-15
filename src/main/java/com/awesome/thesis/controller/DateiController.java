package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.dto.DateiDTO;
import com.awesome.thesis.logic.application.service.files.DateiService;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.files.DateiInfos;
import com.awesome.thesis.logic.domain.model.profil.DateiValue;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
public class DateiController {

    private final DateiService dateiService;
    private final ProfilEditor profilEditor;
    private final ThemaEditor themaEditor;

    public DateiController(DateiService dateiService, ProfilEditor profilEditor, ThemaEditor themaEditor) {
        this.dateiService = dateiService;
        this.profilEditor = profilEditor;
        this.themaEditor = themaEditor;
    }

    @GetMapping("/datei/create")
    public String showForm() {
        return "upload";
    }

    @GetMapping("thema/datei/{id}/create")
    public String showThemaForm(@PathVariable String id, Model model, OAuth2AuthenticationToken auth) {
        Thema thema = themaEditor.getThema(id);
        Integer profilID = auth.getPrincipal().getAttribute("id");
        if(!themaEditor.allowedEdit(profilID, thema)) {
            return "redirect:/";
        }
        model.addAttribute("id", id);
        return "themen/uploadThema";
    }

    @PostMapping("/datei/create")
    public String annehmen(@RequestParam("datei") MultipartFile multipartFile,
                           @RequestParam(value = "beschreibung", required = false) String beschreibung,
                           OAuth2AuthenticationToken auth,
                           Model model) {
        try {
            DateiInfos infos = dateiService.infosErstellen(multipartFile, beschreibung);

            Integer id = auth.getPrincipal().getAttribute("id");
            String dateiId = UUID.randomUUID().toString();
            DateiValue dateiValue = new DateiValue(dateiId, infos.getTitle(), infos.getDescription());
            profilEditor.addDatei(id, dateiValue.id(),dateiValue.name(), dateiValue.beschreibung());

            model.addAttribute("dateiInfos", infos);
            model.addAttribute("nachricht", infos.getTitle() + " wurde erfolgreich hochgeladen.");

            return "upload";
        } catch (IllegalArgumentException e) {
            model.addAttribute("nachricht", e.getMessage());
            return "upload";
        }
    }

    @PostMapping("thema/datei/{id}/create")
    public String themaAnnehmen (@PathVariable String id, @RequestParam("datei") MultipartFile multipartFile,
                                 @RequestParam(value = "beschreibung", required = false) String beschreibung,
                                 OAuth2AuthenticationToken auth,
                                 Model model){
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if(!themaEditor.allowedEdit(profilID, thema)) {
            return "redirect:/";
        }
        try {
            DateiInfos infos = dateiService.infosErstellen(multipartFile, beschreibung);
            String dateiId = UUID.randomUUID().toString();
            DateiDTO dateiDTO = new DateiDTO(dateiId, infos.getTitle(), infos.getDescription());
            themaEditor.addDatei(id, dateiDTO);

            model.addAttribute("dateiInfos", infos);
            model.addAttribute("nachricht", infos.getTitle() + " wurde erfolgreich hochgeladen.");

            return "themen/uploadThema";
        } catch (IllegalArgumentException e) {
            model.addAttribute("nachricht", e.getMessage());
            return "themen/uploadThema";
        }
    }
}
