package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.controller.dto.FachgebietDto;
import com.awesome.thesis.controller.dto.LinkDto;
import com.awesome.thesis.controller.dto.ThemaInfoDto;
import com.awesome.thesis.logic.application.service.html.HtmlService;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaLink;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller, um Daten zu Themen zu editieren.
 */
@Controller
@Secured("ROLE_BETREUENDE")
public class BetreuendeThemaEditorController {

  @Autowired
  ThemaEditor themaEditor;

  @Autowired
  VoraussetzungenEditor vorEditor;

  @Autowired
  HtmlService service;

  /**
   * Methode für GetMapping auf die ThemaEdit-Seite.
   *
   * @param id    Die Id des Themas.
   * @param model {@link Model}
   * @param auth  {@link OAuth2AuthenticationToken} Variable, welche die Github-Id gespeichert hat.
   * @return themaEdit.html, Template zum Editieren von Themen.
   */
  @GetMapping("/themaEdit/{id}")
  public String editThema(@PathVariable("id") Integer id, Model model,
      OAuth2AuthenticationToken auth) {
    int profilId = getId(auth);
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, id)) {
      return "redirect:/";
    }
    ThemaInfoDto info = new ThemaInfoDto(thema.getTitel(), thema.getBeschreibung());
    model.addAttribute("themaInfoDTO", info);
    model.addAttribute("thema", themaEditor.getThema(id));
    model.addAttribute("themaLinkDTO", new LinkDto("", ""));
    model.addAttribute("fachgebietDto", new FachgebietDto(""));
    model.addAttribute("themaVoraussetzungen", themaEditor.getVoraussetzungen(id));
    model.addAttribute("voraussetzungen", vorEditor.getAll());
    return "betreuende/themaEdit";
  }

  /**
   * Methode für PostMapping, um Titel und Beschreibung eines Themas zu verändern.
   *
   * @param id Die Id des Themas.
   * @param themaInfoDto {@link ThemaInfoDto} um neue Themendaten zu erhalten/speichern.
   * @param result {@link BindingResult} um mit fehlschlagender Validierung umzugehen
   * @param model {@link Model}
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return themaEdit.html mit verändertem Titel/Beschreibung.
   */
  @PostMapping("themaEdit/{id}/editInfo")
  public String editThemaInfo(@PathVariable Integer id,
      @Valid @ModelAttribute("themaInfoDTO") ThemaInfoDto themaInfoDto, BindingResult result,
      Model model, OAuth2AuthenticationToken auth) {
    int profilId = getId(auth);
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, id)) {
      return "redirect:/";
    }
    if (result.hasErrors()) {
      model.addAttribute("themaLinkDTO", new LinkDto("", ""));
      model.addAttribute("thema", thema);
      model.addAttribute("themaVoraussetzungen", themaEditor.getVoraussetzungen(id));
      model.addAttribute("voraussetzungen", vorEditor.getAll());
      model.addAttribute("fachgebietDto", new FachgebietDto(""));
      return "betreuende/themaEdit";
    }
    themaEditor.editTitel(profilId, id, themaInfoDto.titel());
    themaEditor.editBeschreibung(id, service.markdownToHtml(themaInfoDto.beschreibung()));
    return "redirect:/themaEdit/" + id;
  }

  /**
   * Methode für PostMapping, um Links zu einem Thema hinzuzufügen.
   *
   * @param id Die Id des Themas.
   * @param dto {@link LinkDto} um neue Links zu erhalten.
   * @param result {@link BindingResult} um mit fehlschlagender Validierung umzugehen
   * @param model {@link Model}
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return themaEdit.html mit hinzugefügtem Link.
   */
  @PostMapping("/themaEdit/{id}/editLink")
  public String editThemaLink(@PathVariable Integer id,
      @Valid @ModelAttribute("themaLinkDTO") LinkDto dto, BindingResult result, Model model,
      OAuth2AuthenticationToken auth) {
    int profilId = getId(auth);
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, id)) {
      return "redirect:/";
    }
    if (result.hasErrors()) {
      ThemaInfoDto info = new ThemaInfoDto(thema.getTitel(), thema.getBeschreibung());
      model.addAttribute("themaInfoDTO", info);
      model.addAttribute("thema", themaEditor.getThema(id));
      model.addAttribute("fachgebietDto", new FachgebietDto(""));
      model.addAttribute("themaVoraussetzungen", themaEditor.getVoraussetzungen(id));
      model.addAttribute("voraussetzungen", vorEditor.getAll());
      return "betreuende/themaEdit";
    }
    themaEditor.addLink(id, dto.url(), dto.urlBeschreibung());
    return "redirect:/themaEdit/" + id;
  }

  /**
   * Methode für PostMapping, um einen Link vom Thema zu löschen.
   *
   * @param link Der Link, der gelöscht werden soll.
   * @param id Die Id des Themas.
   * @param dto {@link LinkDto} um Links zu erhalten.
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return themaEdit.html mit gelöschtem Link.
   */
  @PostMapping("/themaEdit/{id}/deleteLink")
  public String deleteLink(@ModelAttribute ThemaLink link, @PathVariable Integer id,
      @ModelAttribute("themaLinkDTO") LinkDto dto, OAuth2AuthenticationToken auth) {
    int profilId = getId(auth);
    if (!themaEditor.allowedEdit(profilId, id)) {
      return "redirect:/";
    }
    themaEditor.removeLink(id, link);
    return "redirect:/themaEdit/" + id;
  }

  /**
   * Stellt ein Thema dar.
   *
   * @param id Die id des Themas.
   * @return thema.html
   */
  @PostMapping("/themaAnsicht/{id}")
  public String returnToThema(@PathVariable String id) {
    return "redirect:/thema/" + id;
  }

  /**
   * Methode für PostMapping, um Voraussetzungen an einem Thema zu editieren.
   *
   * @param voraussetzungen Die Voraussetzung
   * @param id Die Id des Themas.
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return themaEdit.html mit editierter Voraussetzung.
   */
  @PostMapping("/themaEdit/{id}/editVoraussetzung")
  public String editVoraussetzung(@RequestParam(required = false) Set<String> voraussetzungen,
      @PathVariable Integer id, OAuth2AuthenticationToken auth) {
    int profilId = getId(auth);
    if (!themaEditor.allowedEdit(profilId, id)) {
      return "redirect:/";
    }
    themaEditor.updateVoraussetzungen(id, voraussetzungen);
    return "redirect:/themaEdit/" + id;
  }

  /**
   * Ein GetMapping zu einer Konfirmationsseite.
   *
   * @param id Die Id des Themas.
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @param model {@link Model}
   * @return Ein Redirect, um ein Thema endgültig zu löschen.
   */
  @GetMapping("/thema/{id}/confirmDeletion")
  public String checkDeleteThema(@PathVariable Integer id, OAuth2AuthenticationToken auth,
      Model model) {
    int profilId = getId(auth);
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, id)) {
      return "redirect:/";
    }
    boolean canEdit = themaEditor.allowedEdit(profilId, id);
    model.addAttribute("thema", thema);
    model.addAttribute("canEdit", canEdit);
    return "themen/confirmThemaDeletion";
  }

  /**
   * PostMapping, um ein Thema zu löschen.
   *
   * @param id Die Id des Themas.
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return Die profilEdit.html.
   */
  @PostMapping("/thema/{id}/deleteThema")
  public String deleteThema(@PathVariable Integer id, OAuth2AuthenticationToken auth) {
    int profilId = getId(auth);
    if (!themaEditor.allowedEdit(profilId, id)) {
      return "redirect:/";
    }
    themaEditor.deleteThema(id, profilId);
    return "redirect:/betreuende/profilEdit";
  }

  /**
   * PostMapping, um zu einem Thema ein Fachgebiet hinzuzufügen.
   *
   * @param id Die Id des Themas
   * @param fachgebietDto {@link FachgebietDto}  Um Fachgebiete zu erhalten.
   * @param result {@link BindingResult} um mit fehlschlagender Validierung umzugehen
   * @param model {@link Model}
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return themaEdit.htm
   */
  @PostMapping("/themaEdit/{id}/addFachgebiet")
  public String addFachgebiet(@PathVariable Integer id,
      @Valid @ModelAttribute FachgebietDto fachgebietDto, BindingResult result, Model model,
      OAuth2AuthenticationToken auth) {
    int profilId = getId(auth);
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, id)) {
      return "redirect:/";
    }
    if (result.hasErrors()) {
      ThemaInfoDto info = new ThemaInfoDto(thema.getTitel(), thema.getBeschreibung());
      model.addAttribute("themaInfoDTO", info);
      model.addAttribute("thema", thema);
      model.addAttribute("themaLinkDTO", new LinkDto("", ""));
      model.addAttribute("themaVoraussetzungen", themaEditor.getVoraussetzungen(id));
      model.addAttribute("voraussetzungen", vorEditor.getAll());
      return "betreuende/themaEdit";
    }
    themaEditor.addFachgebiet(id, fachgebietDto.fachgebiet());
    return "redirect:/themaEdit/" + id;
  }

  /**
   * PostMapping, um ein Fachgebiet von einem Thema zu löschen.
   *
   * @param id Die Id des Themas.
   * @param fachgebiet Das Fachgebiet, welches gelöscht werden soll.
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return themaEdit.html
   */
  @PostMapping("/themaEdit/{id}/removeFachgebiet")
  public String removeFachgebiet(@PathVariable Integer id, String fachgebiet,
      OAuth2AuthenticationToken auth) {
    int profilId = getId(auth);
    if (!themaEditor.allowedEdit(profilId, id)) {
      return "redirect:/";
    }
    themaEditor.removeFachgebiet(id, fachgebiet);
    return "redirect:/themaEdit/" + id;
  }

  private int getId(OAuth2AuthenticationToken auth) {
    return (int) Optional.ofNullable(auth.getPrincipal().getAttribute("id"))
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
  }
}
