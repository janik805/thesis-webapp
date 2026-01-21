package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.files.DateiService;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.files.DateiInfos;
import com.awesome.thesis.logic.domain.model.profil.ProfilDateiValue;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaDateiValue;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Ist ein Datei-Controller, der Up- und Download managed.
 */
@Controller
public class DateiController {

  private final DateiService dateiService;
  private final ProfilEditor profilEditor;
  private final ThemaEditor themaEditor;

  /**
   * Konstruktor, um ein DateiController-Objekt zu erstellen.
   *
   * @param dateiService Objekt, dass Dateien verwaltet.
   * @param profilEditor Objekt, dass Profil-Dateien verwaltet.
   * @param themaEditor Objekt, dass Thema-Dateien verwaltet.
   */
  public DateiController(DateiService dateiService,
                         ProfilEditor profilEditor,
                         ThemaEditor themaEditor) {
    this.dateiService = dateiService;
    this.profilEditor = profilEditor;
    this.themaEditor = themaEditor;
  }

  /**
   * GetMapping für Datei-Upload.
   *
   * @return leitet an die upload.html weiter.
   */
  @GetMapping("/datei/create")
  public String showForm() {
    return "upload";
  }

  /**
   * GetMapping für Datei-Upload bei Themen.
   *
   * @param id Id des Themas.
   * @param model Model-Attribute um Id des Themas zu speichern.
   * @param auth Authentifizierungstoken.
   * @return ruft themen/uploadThema.html auf.
   */
  @GetMapping("thema/datei/{id}/create")
  public String showThemaForm(@PathVariable Integer id,
                              Model model,
                              OAuth2AuthenticationToken auth) {
    Thema thema = themaEditor.getThema(id);
    Integer profilId = auth.getPrincipal().getAttribute("id");
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    model.addAttribute("id", id);
    return "themen/uploadThema";
  }

  /**
   * PostMapping um Datei hochzuladen.
   *
   * @param multipartFile Datei.
   * @param beschreibung Beschreibung.
   * @param auth Authentifizierungstoken.
   * @param model Model-Attribute um Datei-Infos zu speichern
   *              und Nachricht zu speichern, die nach Upload angezeigt wird.
   * @return Redirected zur profilEdit.html bei erfolgreichem Upload.
   *              Sonst wird zu upload.html weitergeleitet.
   */
  @PostMapping("/datei/create")
  public String annehmen(@RequestParam("datei") MultipartFile multipartFile,
                         @RequestParam(value = "beschreibung", required = false)
                         String beschreibung,
                         OAuth2AuthenticationToken auth,
                         Model model) {
    try {
      DateiInfos infos = dateiService.dateiSpeichern(multipartFile, beschreibung);

      Integer id = auth.getPrincipal().getAttribute("id");
      String dateiId = UUID.randomUUID().toString();
      ProfilDateiValue dateiValue = new ProfilDateiValue(dateiId,
          infos.getTitle(),
          infos.getDescription());
      profilEditor.addDatei(id, dateiValue.id(), dateiValue.name(), beschreibung);

      model.addAttribute("dateiInfos", infos);
      model.addAttribute("nachricht", infos.getTitle() + " wurde erfolgreich hochgeladen.");

      return "redirect:/betreuende/profilEdit";
    } catch (IllegalArgumentException e) {
      model.addAttribute("nachricht", e.getMessage());
      return "upload";
    }
  }

  /**
   * PostMapping, um einen Datei-Upload zu einem Thema anzunehmen.
   *
   * @param id Id des Themas.
   * @param multipartFile Datei.
   * @param beschreibung Beschreibung.
   * @param auth Authentifizierungstoken.
   * @param model Model-Attribute um Datei-Infos zu speichern
   *              und Nachricht zu speichern, die nach Upload angezeigt wird.
   * @return Gibt die Startseite zurück, wenn Thema nicht editiert werden darf.
   *              Sonst wird themen/uploadThema.html aufgerufen.
   */
  @PostMapping("thema/datei/{id}/create")
  public String themaAnnehmen(@PathVariable Integer id,
                              @RequestParam("datei") MultipartFile multipartFile,
                              @RequestParam(value = "beschreibung", required = false)
                                String beschreibung,
                              OAuth2AuthenticationToken auth,
                              Model model) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    try {
      DateiInfos infos = dateiService.dateiSpeichern(multipartFile, beschreibung);
      String dateiId = UUID.randomUUID().toString();
      ThemaDateiValue dateiValue = new ThemaDateiValue(
          dateiId,
          infos.getTitle(),
          infos.getDescription());
      themaEditor.addDatei(id, dateiValue);

      model.addAttribute("dateiInfos", infos);
      model.addAttribute("nachricht", infos.getTitle() + " wurde erfolgreich hochgeladen.");

      return "themen/uploadThema";
    } catch (IllegalArgumentException e) {
      model.addAttribute("nachricht", e.getMessage());
      return "themen/uploadThema";
    }
  }

  /**
   * PostMapping, um eine Datei mit einer Id zu löschen.
   *
   * @param dateiValue speichert fachlich die Infos einer Datei.
   * @param id Id der Datei.
   * @param auth Authentifizierungstoken.
   * @return Redirected zur profilEdit.html.
   */
  @PostMapping("/datei/{id}/delete")
  public String deleteProfilDatei(@ModelAttribute ProfilDateiValue dateiValue,
                                  @PathVariable String id,
                                  OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");

    profilEditor.removeDatei(profilId, id);
    return "redirect:/betreuende/profilEdit";
  }

  /**
   * GetMapping um eine Datei herunterzuladen.
   *
   * @param filename Name der Datei.
   * @return gibt eine ResponseEntity zurück, die alle Daten enhält,
   *      die der Browser benötigt, um die zu downloadende Datei bereitzustellen.
   */
  @GetMapping("/datei/download/{filename}")
  public ResponseEntity<Resource> downloadDatei(@PathVariable String filename) {
    Resource datei = dateiService.dateiLaden(filename);

    return ResponseEntity.ok()
        .header("Content-Disposition", "attachment; filename=\"" + datei.getFilename() + "\"")
        .body(datei);
  }

}
