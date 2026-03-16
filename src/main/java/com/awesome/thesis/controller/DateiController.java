package com.awesome.thesis.controller;

import com.awesome.thesis.logic.application.service.files.DateiService;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.files.DateiInfos;
import com.awesome.thesis.logic.domain.model.profil.ProfilDateiValue;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaDateiValue;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
  @SuppressFBWarnings(value = "EI_EXPOSE_REP",
      justification = "Spring Konstruktor Injection")
  private final DateiService dateiService;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP",
      justification = "Spring Konstruktor Injection")
  private final ProfilEditor profilEditor;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP",
      justification = "Spring Konstruktor Injection")
  private final ThemaEditor themaEditor;

  /**
   * Der Konstruktor, der benutzt wird, um ein DateiController-Objekt zu erstellen.
   *
   * @param dateiService Ein Objekt, das Dateien verwaltet.
   * @param profilEditor Ein Objekt, das Profil-Dateien verwaltet.
   * @param themaEditor Ein Objekt, das Thema-Dateien verwaltet.
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
   * @param id Die Id des Themas.
   * @param model Ein Model-Attribut, um Id des Themas zu speichern.
   * @param auth Ein Authentifizierungstoken.
   * @return ruft themen/uploadThema.html auf.
   */
  @GetMapping("thema/datei/{id}/create")
  public String showThemaForm(@PathVariable Integer id,
                              Model model,
                              OAuth2AuthenticationToken auth) {
    Thema thema = themaEditor.getThema(id);
    Integer profilId = auth.getPrincipal().getAttribute("id");
    if (profilId == null || !themaEditor.allowedEdit(profilId, id)) {
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
      if (id == null) {
        model.addAttribute("nachricht", "Bitte anmelden, um Dateien hochzuladen.");
        return "redirect:/";
      }
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
   * @param id Die Id des Themas.
   * @param multipartFile Eine Datei.
   * @param beschreibung Beschreibung.
   * @param auth Authentifizierungstoken.
   * @param model Model-Attribute um Datei-Infos zu speichern
   *              und Nachricht zu speichern, die nach Upload angezeigt wird.
   * @return Gibt die Startseite zurück, falls das Thema nicht editiert werden darf.
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
    if (profilId == null || !themaEditor.allowedEdit(profilId, id)) {
      throw new IllegalStateException("keine profilId vorhanden.");
    }
    try {
      DateiInfos infos = dateiService.dateiSpeichern(multipartFile, beschreibung);
      String dateiId = UUID.randomUUID().toString();
      themaEditor.addDatei(id, dateiId, infos.getTitle(), infos.getDescription());

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
   * @param dateiValue speichert fachlich die Informationen einer Datei.
   * @param id Id der Datei.
   * @param auth Authentifizierungstoken.
   * @return Redirected zur profilEdit.html.
   */
  @PostMapping("/datei/{id}/delete")
  public String deleteProfilDatei(@ModelAttribute ProfilDateiValue dateiValue,
                                  @PathVariable String id,
                                  OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    if (profilId == null) {
      throw new IllegalStateException("Keine Id vorhanden.");
    }

    profilEditor.removeDatei(profilId, id);
    return "redirect:/betreuende/profilEdit";
  }

  /**
   * PostMapping, um eine Datei von einem Thema zu löschen.
   *
   * @param id Die Id der Datei
   * @param themaId Die Id des Themas
   * @param auth Die Github-Id
   * @return Datei wird gelöscht
   */
  @PostMapping("/datei/{id}/{themaId}/delete")
  public String deleteThemaDatei(@PathVariable String id,
      @PathVariable Integer themaId, OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    if (profilId == null) {
      throw new IllegalStateException("Keine Id vorhanden.");
    }
    themaEditor.removeDatei(themaId, id);
    return "redirect:/themaEdit/" + themaId;
  }

  /**
   * GetMapping um eine Datei herunterzuladen.
   *
   * @param filename Name der Datei.
   * @return gibt eine ResponseEntity zurück, die alle Daten enthält,
   *      die der Browser benötigt, um die zu downloadende Datei bereitzustellen.
   */
  @GetMapping("/datei/download/{filename}")
  public ResponseEntity<Resource> downloadDatei(@PathVariable String filename) {
    Resource datei = dateiService.dateiLaden(filename);

    return ResponseEntity.ok()
        .header("Content-Disposition", "attachment; filename=\"" + datei.getFilename() + "\"")
        .body(datei);
  }

  /**
   * Eine Methode für das Umwandeln von Markdown in HTML.
   *
   * @param filename Name der Datei.
   * @return gibt die umgewandelte Datei zurück für den Download.
   */
  @GetMapping("/datei/view/{filename}")
  public ResponseEntity<?> markdownAlsHtml(@PathVariable String filename) {
    if (filename.toLowerCase().endsWith(".md")) {
      String htmlString = dateiService.markdownZuHtml(filename);
      return ResponseEntity.ok()
          .header("Content-Type", "text/html; charset=UTF-8")
          .body(htmlString);
    }
    return downloadDatei(filename);
  }

}
