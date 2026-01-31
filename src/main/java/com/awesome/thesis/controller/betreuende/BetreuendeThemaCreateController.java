package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.controller.dto.ThemaInfoDto;
import com.awesome.thesis.logic.application.service.html.HtmlService;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller zum Erstellen von Themen.
 */
@Controller
@Secured("ROLE_BETREUENDE")
public class BetreuendeThemaCreateController {

  @Autowired
  ThemaEditor themaEditor;

  @Autowired
  HtmlService service;

  /**
   * GetMapping auf die themaCreate-Seite.
   *
   * @param model {@link Model}
   * @return themaCreate.html
   */
  @GetMapping("/thema/create")
  public String getSite(Model model) {
    model.addAttribute(new ThemaInfoDto("", ""));
    return "betreuende/themaCreate";
  }

  /**
   * Ein PostMapping, um ein Thema zu erstellen.
   *
   * @param redirect {@link RedirectAttributes} Zur Umleitung auf themaEdit
   * @param dto {@link ThemaInfoDto} Für Informationen zum Thema.
   * @param result {@link BindingResult} um mit fehlschlagender Validierung umzugehen
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return themaEdit.html um weitere Informationen zum Thema hinzuzufügen.
   */
  @PostMapping("/thema/create")
  public String postThema(RedirectAttributes redirect,
      @Valid @ModelAttribute("themaInfoDTO") ThemaInfoDto dto, BindingResult result,
      OAuth2AuthenticationToken auth) {
    if (result.hasErrors()) {
      return "betreuende/themaCreate";
    }
    int profilId = getId(auth);
    Thema thema = new Thema(dto.titel(), profilId);
    Thema savedThema = themaEditor.addThema(thema, profilId);
    Integer themaId = savedThema.getId();
    themaEditor.editBeschreibung(themaId, service.markdownToHtml(dto.beschreibung()));
    redirect.addFlashAttribute("themaInfoDTO", dto);
    return "redirect:/themaEdit/" + themaId;
  }

  private int getId(OAuth2AuthenticationToken auth) {
    return (int) Optional.ofNullable(auth.getPrincipal().getAttribute("id"))
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
  }
}
