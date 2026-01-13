package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.controller.dto.FachgebietDTO;
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

import java.util.*;

@Controller
@Secured("ROLE_BETREUENDE")
public class BetreuendeThemaEditorController {

    @Autowired
    ThemaEditor themaEditor;

    @Autowired
    ProfilEditor profilEditor;

    @Autowired
    VoraussetzungenEditor vorEditor;

    @GetMapping("/themaEdit/{id}")
    public String editThema(@PathVariable("id") String id, Model model, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (themaEditor.allowedEdit(profilID, thema)) {
            ThemaInfoDTO info = new ThemaInfoDTO(thema.getTitel(), thema.getBeschreibung());
            model.addAttribute("themaInfoDTO", info);
            model.addAttribute("thema", themaEditor.getThema(id));
            model.addAttribute("themaLinkDTO", new LinkDTO("", ""));
            model.addAttribute("fachgebietDTO", new FachgebietDTO(""));
            model.addAttribute("profilID", profilID);
            model.addAttribute("themaVoraussetzungen", thema.getVoraussetzungen());
            model.addAttribute("voraussetzungen", vorEditor.getAll());
            return "betreuende/themaEdit";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("themaEdit/{id}/editInfo")
    public String editThemaInfo(@PathVariable String id, @Valid @ModelAttribute("themaInfoDTO") ThemaInfoDTO themaInfoDTO, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (result.hasErrors()) {
            model.addAttribute("themaLinkDTO", new LinkDTO("", ""));
            model.addAttribute("thema", thema);
            model.addAttribute("profilID", profilID);
            model.addAttribute("themaVoraussetzungen", thema.getVoraussetzungen());
            model.addAttribute("voraussetzungen", vorEditor.getAll());
            model.addAttribute("fachgebietDTO", new FachgebietDTO(""));
            return "betreuende/themaEdit";
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
        if (result.hasErrors()) {
            ThemaInfoDTO info = new ThemaInfoDTO(thema.getTitel(), thema.getBeschreibung());
            model.addAttribute("themaInfoDTO", info);
            model.addAttribute("thema", themaEditor.getThema(id));
            model.addAttribute("fachgebietDTO", new FachgebietDTO(""));
            model.addAttribute("profilID", profilID);
            model.addAttribute("themaVoraussetzungen", thema.getVoraussetzungen());
            model.addAttribute("voraussetzungen", vorEditor.getAll());
            return "betreuende/themaEdit";
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

    @PostMapping("/themaEdit/{id}/editVoraussetzung")
    public String editVoraussetzung(@RequestParam(required = false) Optional<Set<String>> voraussetzungen, @PathVariable String id, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (themaEditor.allowedEdit(profilID, thema)) {
            Set<Voraussetzung> list = new HashSet<>();
            voraussetzungen.orElse(Set.of()).forEach(e -> list.add(new Voraussetzung(e)));
            themaEditor.updateVoraussetzungen(id, list);
            return "redirect:/themaEdit/" + id;
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/thema/{id}/confirmDeletion")
    public String checkDeleteThema(@PathVariable String id, OAuth2AuthenticationToken auth, Model model) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (themaEditor.allowedEdit(profilID, thema)) {
            boolean canEdit = themaEditor.allowedEdit(profilID, thema);
            model.addAttribute("thema", thema);
            model.addAttribute("canEdit", canEdit);
            return "themen/confirmThemaDeletion";
        } else {
            return "redirect:/thema" + id;
        }
    }

    @PostMapping("/thema/{id}/deleteThema")
    public String deleteThema(@PathVariable String id, OAuth2AuthenticationToken auth, Model model) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (themaEditor.allowedEdit(profilID, thema)) {
            profilEditor.removeThema(profilID, new ThemaDTO(id, thema.getTitel()));
            themaEditor.deleteThema(id);
            return "redirect:/betreuende/profilEdit";
        } else {
            return "redirect:/thema" + id;
        }
    }

    @PostMapping("/themaEdit/{id}/addFachgebiet")
    public String addFachgebiet(@PathVariable String id, @Valid @ModelAttribute FachgebietDTO fachgebietDTO, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (themaEditor.allowedEdit(profilID, thema)) {
            if (result.hasErrors()) {
                ThemaInfoDTO info = new ThemaInfoDTO(thema.getTitel(), thema.getBeschreibung());
                model.addAttribute("themaInfoDTO", info);
                model.addAttribute("thema", thema);
                model.addAttribute("themaLinkDTO", new LinkDTO("", ""));
                model.addAttribute("profilID", profilID);
                model.addAttribute("themaVoraussetzungen", thema.getVoraussetzungen());
                model.addAttribute("voraussetzungen", vorEditor.getAll());
                return "redirect:/themaEdit/" + id;
            }
            themaEditor.addFachgebiet(id, fachgebietDTO.fachgebiet());
            return "redirect:/themaEdit/" + id;
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/themaEdit/{id}/removeFachgebiet")
    public String removeFachgebiet(@PathVariable String id, String fachgebiet, OAuth2AuthenticationToken auth) {
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = themaEditor.getThema(id);
        if (themaEditor.allowedEdit(profilID, thema)) {
            themaEditor.removeFachgebiet(id, fachgebiet);
            return "redirect:/themaEdit/" + id;
        } else {
            return "redirect:/";
        }
    }
}
