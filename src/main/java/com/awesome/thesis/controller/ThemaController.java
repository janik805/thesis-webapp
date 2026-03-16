package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.fachgebiete.FachgebieteEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller, um die Themenliste und einzelne Themen darzustellen.
 */
@Controller
public class ThemaController {

  @Autowired
  ThemaEditor editor;

  @Autowired
  FachgebieteEditor fachEditor;

  @Autowired
  VoraussetzungenEditor vorEditor;

  /**
   * Methode für Post-Mapping auf Themen.
   *
   * @param model Das Model.
   * @param interessen Die Interessen, die eingegeben werden, falls gefiltert werden soll.
   * @param voraussetzungen Die Voraussetzungen, die eingegeben werden, falls gefiltert werden soll.
   * @return Die Themen-Seite.
   */
  @GetMapping("/themen")
  public String themenListe(Model model, @RequestParam(required = false) Set<String> interessen,
      @RequestParam(required = false) Set<String> voraussetzungen) {
    model.addAttribute("themenListe", editor.getFitting(voraussetzungen, interessen));
    model.addAttribute("fachgebiete", fachEditor.getAll());
    model.addAttribute("voraussetzungen", vorEditor.getAllString());
    model.addAttribute("interessen", interessen);
    model.addAttribute("eingabeVoraussetzungen", voraussetzungen);
    return "themen/themen";
  }

  /**
   * Methode für Get-Mapping auf Thema.
   *
   * @param id Die Id des Themas.
   * @param model Das Model.
   * @param auth Die Variable, die die Github-Id speichert.
   * @return Die Thema-Seite.
   */
  @GetMapping("/thema/{id}")
  public String thema(@PathVariable("id") Integer id, Model model, OAuth2AuthenticationToken auth) {
    Thema thema = editor.getThema(id);
    int profilId = getId(auth);
    boolean canEdit = editor.allowedEdit(profilId, id);
    model.addAttribute("thema", thema);
    model.addAttribute("canEdit", canEdit);
    model.addAttribute("profilID", thema.getProfilId());
    return "themen/thema";
  }

  private int getId(OAuth2AuthenticationToken auth) {
    return (int) Optional.ofNullable(auth.getPrincipal().getAttribute("id"))
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
  }
}
