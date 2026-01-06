package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.dto.DateiDTO;
import com.awesome.thesis.logic.application.service.files.DateiService;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.files.DateiInfos;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
public class DateiController {

    private final DateiService dateiService;
    private final ProfilEditor profilEditor;

    public DateiController(DateiService dateiService, ProfilEditor profilEditor) {
        this.dateiService = dateiService;
        this.profilEditor = profilEditor;
    }

    @GetMapping("/datei/create")
    public String showForm() {
        return "upload";
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
            DateiDTO dateiDTO = new DateiDTO(dateiId, infos.getTitle(), infos.getDescription());
            profilEditor.addDatei(id.longValue(), dateiDTO);

            model.addAttribute("dateiInfos", infos);
            model.addAttribute("nachricht", infos.getTitle() + " wurde erfolgreich hochgeladen.");

            return "upload";
        } catch (IllegalArgumentException e) {
            model.addAttribute("nachricht", e.getMessage());
            return "upload";
        }
    }
}
