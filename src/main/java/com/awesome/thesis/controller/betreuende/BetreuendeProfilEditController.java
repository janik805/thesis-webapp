package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.controller.dto.FachgebietDto;
import com.awesome.thesis.controller.dto.LinkDto;
import com.awesome.thesis.controller.dto.ProfilEditDto;
import com.awesome.thesis.controller.dto.kontakt.EmailKontaktDto;
import com.awesome.thesis.controller.dto.kontakt.TelKontaktDto;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.ProfilKontakt;
import com.awesome.thesis.logic.domain.model.profil.ProfilLink;
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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller für die Bearbeitung eines Betreuendenprofils.
 */
@Controller
@RequestMapping("/betreuende")
@Secured("ROLE_BETREUENDE")
public class BetreuendeProfilEditController {
  @Autowired
  ProfilEditor editor;
  
  /**
   * Methode für das Get-Mapping auf profilEdit.
   *
   * @param model {@link Model}
   * @param auth  {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return profilEdit.html Template mit Profil Daten
   */
  @GetMapping("profilEdit")
  public String profilEdit(Model model, OAuth2AuthenticationToken auth) {
    Integer idNull = auth.getPrincipal().getAttribute("id");
    int id = Optional.ofNullable(idNull)
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
    model.addAttribute("profil", editor.get(id));
    model.addAttribute("kontakt", new EmailKontaktDto("email", "", ""));
    model.addAttribute("linkDTO", new LinkDto("", ""));
    model.addAttribute("profilEditDTO", new ProfilEditDto(""));
    model.addAttribute("fachgebietDTO", new FachgebietDto(""));
    return "betreuende/profilEdit";
  }
  
  /**
   * Methode für das Post-Mapping der Namensänderung.
   *
   * @param profilEditDto {@link ProfilEditDto} um neue Nutzerdaten zu erhalten
   * @param result        {@link BindingResult} um mit fehlschlagender Validierung umzugehen
   * @param model         {@link Model}
   * @param auth          {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return redirect auf betreuende/profilEdit und bei Fehlern im BindingResult profilEdit.html
   */
  @PostMapping("profilEdit")
  public String profilEdit(@Valid @ModelAttribute() ProfilEditDto profilEditDto,
                           BindingResult result, Model model, OAuth2AuthenticationToken auth) {
    Integer idNull = auth.getPrincipal().getAttribute("id");
    int id = Optional.ofNullable(idNull)
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
    if (result.hasErrors()) {
      model.addAttribute("profil", editor.get(id));
      model.addAttribute("kontakt", new EmailKontaktDto("email", "", ""));
      model.addAttribute("linkDTO", new LinkDto("", ""));
      model.addAttribute("fachgebietDTO", new FachgebietDto(""));
      return "betreuende/profilEdit";
    }
    editor.editName(id, profilEditDto.name());
    return "redirect:/betreuende/profilEdit";
  }
  
  /**
   * Methode für post-Mapping um Kontakt zu löschen.
   *
   * @param profilKontakt {@link ProfilKontakt} um Kontakt zu laden
   * @param auth          {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return redirect auf /betreuende/profilEdit
   */
  @PostMapping("profilEdit/deleteKontakt")
  public String deleteKontakt(@ModelAttribute ProfilKontakt profilKontakt,
                              OAuth2AuthenticationToken auth) {
    Integer idNull = auth.getPrincipal().getAttribute("id");
    int id = Optional.ofNullable(idNull)
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
    editor.removeKontakt(id, profilKontakt);
    return "redirect:/betreuende/profilEdit";
  }
  
  /**
   * Methode für Post-Mapping um eine E-Mail hinzuzufügen.
   *
   * @param email  {@link EmailKontaktDto} zur Validierung
   * @param result {@link BindingResult} um mit fehlschlagender Validierung umzugehen
   * @param model  {@link Model}
   * @param auth   {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return redirect auf betreuende/profilEdit und bei Fehlern im BindingResult profilEdit.html
   */
  @PostMapping(value = "profilEdit/addKontakt", params = "type=email")
  public String addEmail(@Valid @ModelAttribute("kontakt") EmailKontaktDto email,
                         BindingResult result, Model model, OAuth2AuthenticationToken auth) {
    Integer idNull = auth.getPrincipal().getAttribute("id");
    int id = Optional.ofNullable(idNull)
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
    if (result.hasErrors()) {
      model.addAttribute("profil", editor.get(id));
      model.addAttribute("linkDTO", new LinkDto("", ""));
      model.addAttribute("profilEditDTO", new ProfilEditDto(""));
      model.addAttribute("fachgebietDTO", new FachgebietDto(""));
      return "betreuende/profilEdit";
    }
    editor.addEmail(id, email.label(), email.wert());
    return "redirect:/betreuende/profilEdit";
  }
  
  /**
   * Methode für Post-Mapping um eine E-Mail hinzuzufügen.
   *
   * @param tel    {@link TelKontaktDto} zur Validierung
   * @param result {@link BindingResult} um mit fehlschlagender Validierung umzugehen
   * @param model  {@link Model}
   * @param auth   {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return redirect auf betreuende/profilEdit und bei Fehlern im BindingResult profilEdit.html
   */
  @PostMapping(value = "profilEdit/addKontakt", params = "type=tel")
  public String addTel(@Valid @ModelAttribute("kontakt") TelKontaktDto tel, BindingResult result,
                       Model model, OAuth2AuthenticationToken auth) {
    Integer idNull = auth.getPrincipal().getAttribute("id");
    int id = Optional.ofNullable(idNull)
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
    if (result.hasErrors()) {
      model.addAttribute("profil", editor.get(id));
      model.addAttribute("linkDTO", new LinkDto("", ""));
      model.addAttribute("profilEditDTO", new ProfilEditDto(""));
      model.addAttribute("fachgebietDTO", new FachgebietDto(""));
      return "betreuende/profilEdit";
    }
    editor.addTel(id, tel.label(), tel.wert());
    return "redirect:/betreuende/profilEdit";
  }
  
  /**
   * Methode für Post-Mapping zum Hinzufügen von einem Fachgebiet.
   *
   * @param fachgebietDto {@link FachgebietDto} zur Validierung
   * @param result        {@link BindingResult} um mit fehlschlagender Validierung umzugehen
   * @param model         {@link Model}
   * @param auth          {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return redirect auf betreuende/profilEdit und bei Fehlern im BindingResult profilEdit.html
   */
  @PostMapping("profilEdit/addFachgebiet")
  public String addFachgebiet(@Valid @ModelAttribute() FachgebietDto fachgebietDto,
                              BindingResult result, Model model, OAuth2AuthenticationToken auth) {
    Integer idNull = auth.getPrincipal().getAttribute("id");
    int id = Optional.ofNullable(idNull)
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
    if (result.hasErrors()) {
      model.addAttribute("profil", editor.get(id));
      model.addAttribute("kontakt", new EmailKontaktDto("email", "", ""));
      model.addAttribute("linkDTO", new LinkDto("", ""));
      model.addAttribute("profilEditDTO", new ProfilEditDto(""));
      return "betreuende/profilEdit";
    }
    editor.addFachgebiet(id, fachgebietDto.fachgebiet());
    return "redirect:/betreuende/profilEdit";
  }
  
  /**
   * Methode für Post-Mapping zum Löschen von Fachgebieten.
   *
   * @param fachgebiet Name von Fachgebiet
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return redirect auf betreuende/profilEdit
   */
  @PostMapping("profilEdit/removeFachgebiet")
  public String removeFachgebiet(String fachgebiet, OAuth2AuthenticationToken auth) {
    Integer idNull = auth.getPrincipal().getAttribute("id");
    int id = Optional.ofNullable(idNull)
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
    editor.removeFachgebiet(id, fachgebiet);
    return "redirect:/betreuende/profilEdit";
  }
  
  /**
   * Methode für Post-Mapping um einen Link hinzuzufügen.
   *
   * @param linkDto {@link LinkDto} zur Validierung
   * @param result {@link BindingResult} um mit fehlschlagender Validierung umzugehen
   * @param model  {@link Model}
   * @param auth   {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return redirect auf betreuende/profilEdit und bei Fehlern im BindingResult profilEdit.html
   */
  @PostMapping("/profilEdit/addLink")
  public String addLink(@Valid @ModelAttribute("linkDTO") LinkDto linkDto, BindingResult result,
                        Model model, OAuth2AuthenticationToken auth) {
    Integer idNull = auth.getPrincipal().getAttribute("id");
    int id = Optional.ofNullable(idNull)
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
    if (result.hasErrors()) {
      model.addAttribute("profil", editor.get(id));
      model.addAttribute("kontakt", new EmailKontaktDto("email", "", ""));
      model.addAttribute("profilEditDTO", new ProfilEditDto(""));
      model.addAttribute("fachgebietDTO", new FachgebietDto(""));
      return "betreuende/profilEdit";
    }
    editor.addLink(id, linkDto.url(), linkDto.urlBeschreibung());
    return "redirect:/betreuende/profilEdit";
  }
  
  /**
   * Methode für Post-Mapping zum Löschen eines Links.
   *
   * @param link {@link ProfilLink}
   * @param auth {@link OAuth2AuthenticationToken} um auf Github-Id zuzugreifen
   * @return redirect auf /betreuende/profilEdit
   */
  @PostMapping("/profilEdit/deleteLink")
  public String deleteLink(@ModelAttribute ProfilLink link, OAuth2AuthenticationToken auth) {
    Integer idNull = auth.getPrincipal().getAttribute("id");
    int id = Optional.ofNullable(idNull)
        .orElseThrow(() -> new OAuth2AuthenticationException("Keine Github-Id"));
    editor.removeLink(id, link);
    return "redirect:/betreuende/profilEdit";
  }
}