package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.controller.dto.FachgebietDto;
import com.awesome.thesis.controller.dto.LinkDTO;
import com.awesome.thesis.controller.dto.ThemaInfoDTO;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.application.service.voraussetzungen.VoraussetzungenEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaLink;
import jakarta.validation.Valid;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Secured("ROLE_BETREUENDE")
public class BetreuendeThemaEditorController {

  @Autowired
  ThemaEditor themaEditor;

  @Autowired
  VoraussetzungenEditor vorEditor;

  @GetMapping("/themaEdit/{id}")
  public String editThema(@PathVariable("id") Integer id, Model model,
      OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    ThemaInfoDTO info = new ThemaInfoDTO(thema.getTitel(), thema.getBeschreibung());
    model.addAttribute("themaInfoDTO", info);
    model.addAttribute("thema", themaEditor.getThema(id));
    model.addAttribute("themaLinkDTO", new LinkDTO("", ""));
    model.addAttribute("fachgebietDTO", new FachgebietDto(""));
    model.addAttribute("themaVoraussetzungen", themaEditor.getVoraussetzungen(id));
    model.addAttribute("voraussetzungen", vorEditor.getAll());
    return "betreuende/themaEdit";
  }

  @PostMapping("themaEdit/{id}/editInfo")
  public String editThemaInfo(@PathVariable Integer id,
      @Valid @ModelAttribute("themaInfoDTO") ThemaInfoDTO themaInfoDto, BindingResult result,
      Model model, OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    if (result.hasErrors()) {
      model.addAttribute("themaLinkDTO", new LinkDTO("", ""));
      model.addAttribute("thema", thema);
      model.addAttribute("themaVoraussetzungen", themaEditor.getVoraussetzungen(id));
      model.addAttribute("voraussetzungen", vorEditor.getAll());
      model.addAttribute("fachgebietDTO", new FachgebietDto(""));
      return "betreuende/themaEdit";
    }
    themaEditor.editTitel(profilId, id, themaInfoDto.titel());
    themaEditor.editBeschreibung(id, themaInfoDto.beschreibung());
    return "redirect:/themaEdit/" + id;
  }

  @PostMapping("/themaEdit/{id}/editLink")
  public String editThemaLink(@PathVariable Integer id,
      @Valid @ModelAttribute("themaLinkDTO") LinkDTO dto, BindingResult result, Model model,
      OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    if (result.hasErrors()) {
      ThemaInfoDTO info = new ThemaInfoDTO(thema.getTitel(), thema.getBeschreibung());
      model.addAttribute("themaInfoDTO", info);
      model.addAttribute("thema", themaEditor.getThema(id));
      model.addAttribute("fachgebietDTO", new FachgebietDto(""));
      model.addAttribute("themaVoraussetzungen", themaEditor.getVoraussetzungen(id));
      model.addAttribute("voraussetzungen", vorEditor.getAll());
      return "betreuende/themaEdit";
    }
    themaEditor.addLink(id, dto.url(), dto.urlBeschreibung());
    return "redirect:/themaEdit/" + id;
  }

  @PostMapping("/themaEdit/{id}/deleteLink")
  public String deleteLink(@ModelAttribute ThemaLink link, @PathVariable Integer id,
      @ModelAttribute("themaLinkDTO") LinkDTO dto, OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    themaEditor.removeLink(id, link);
    return "redirect:/themaEdit/" + id;
  }

  @PostMapping("/themaAnsicht/{id}")
  public String returnToThema(@PathVariable String id) {
    return "redirect:/thema/" + id;
  }

  @PostMapping("/themaEdit/{id}/editVoraussetzung")
  public String editVoraussetzung(@RequestParam(required = false) Set<String> voraussetzungen,
      @PathVariable Integer id, OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    themaEditor.updateVoraussetzungen(id, voraussetzungen);
    return "redirect:/themaEdit/" + id;
  }

  @GetMapping("/thema/{id}/confirmDeletion")
  public String checkDeleteThema(@PathVariable Integer id, OAuth2AuthenticationToken auth,
      Model model) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    boolean canEdit = themaEditor.allowedEdit(profilId, thema);
    model.addAttribute("thema", thema);
    model.addAttribute("canEdit", canEdit);
    return "themen/confirmThemaDeletion";
  }

  @PostMapping("/thema/{id}/deleteThema")
  public String deleteThema(@PathVariable Integer id, OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    themaEditor.deleteThema(id, profilId);
    return "redirect:/betreuende/profilEdit";
  }

  @PostMapping("/themaEdit/{id}/addFachgebiet")
  public String addFachgebiet(@PathVariable Integer id,
                              @Valid @ModelAttribute FachgebietDto fachgebietDto, BindingResult result, Model model,
                              OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    if (result.hasErrors()) {
      ThemaInfoDTO info = new ThemaInfoDTO(thema.getTitel(), thema.getBeschreibung());
      model.addAttribute("themaInfoDTO", info);
      model.addAttribute("thema", thema);
      model.addAttribute("themaLinkDTO", new LinkDTO("", ""));
      model.addAttribute("themaVoraussetzungen", themaEditor.getVoraussetzungen(id));
      model.addAttribute("voraussetzungen", vorEditor.getAll());
      return "betreuende/themaEdit";
    }
    themaEditor.addFachgebiet(id, fachgebietDto.fachgebiet());
    return "redirect:/themaEdit/" + id;
  }

  @PostMapping("/themaEdit/{id}/removeFachgebiet")
  public String removeFachgebiet(@PathVariable Integer id, String fachgebiet,
      OAuth2AuthenticationToken auth) {
    Integer profilId = auth.getPrincipal().getAttribute("id");
    Thema thema = themaEditor.getThema(id);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      return "redirect:/";
    }
    themaEditor.removeFachgebiet(id, fachgebiet);
    return "redirect:/themaEdit/" + id;
  }
}
