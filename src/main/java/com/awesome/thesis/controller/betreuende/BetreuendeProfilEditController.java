package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.controller.dto.LinkDTO;
import com.awesome.thesis.controller.dto.kontakt.EmailKontaktDTO;
import com.awesome.thesis.controller.dto.kontakt.TelKontaktDTO;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.profil.Kontakt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/betreuende")
@Secured("ROLE_BETREUENDE")
public class BetreuendeProfilEditController {
    @Autowired
    ProfilEditor editor;

    @GetMapping("profilEdit")
    public String profilEdit(Model model, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        model.addAttribute("profil", editor.get(id));
        model.addAttribute("kontakt", new EmailKontaktDTO("email","", ""));
        model.addAttribute("linkDTO", new LinkDTO("", ""));
        return "betreuende/profilEdit";
    }

    @PostMapping("profilEdit")
    public String profilEdit(@ModelAttribute("name") String name, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        editor.editName(id, name);
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping("profilEdit/deleteKontakt")
    public String deleteKontakt(@ModelAttribute Kontakt kontakt,OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        editor.removeKontakt(id, kontakt);
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping(value="profilEdit/addKontakt", params="type=email")
    public String addEmail(@Valid @ModelAttribute("kontakt") EmailKontaktDTO email, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        if  (result.hasErrors()) {
            model.addAttribute("profil", editor.get(id));
            model.addAttribute("linkDTO", new LinkDTO("", ""));
            return "betreuende/profilEdit";
        }
        editor.addEmail(id, email.label(), email.wert());
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping(value="profilEdit/addKontakt", params="type=tel")
    public String addTel(@Valid @ModelAttribute("kontakt") TelKontaktDTO tel, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        if  (result.hasErrors()) {
            model.addAttribute("profil", editor.get(id));
            model.addAttribute("linkDTO", new LinkDTO("", ""));
            return "betreuende/profilEdit";
        }
        editor.addTel(id, tel.label(), tel.wert());
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping("profilEdit/addFachgebiet")
    public String addFachgebiet(String fachgebiet, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        editor.addFachgebiet(id, fachgebiet);
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping("profilEdit/removeFachgebiet")
    public String removeFachgebiet(String fachgebiet, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        editor.removeFachgebiet(id, fachgebiet);
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping("/profilEdit/addLink")
    public String editThemaLink(@Valid @ModelAttribute("linkDTO") LinkDTO dto, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        if (result.hasErrors()){
            model.addAttribute("profil", editor.get(id));
            model.addAttribute("kontakt", new EmailKontaktDTO("email","", ""));
            return "betreuende/profilEdit";
        }
        editor.addLink(id, dto.url(), dto.urlBeschreibung());
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping("/profilEdit/deleteLink")
    public String deleteLink(@ModelAttribute Link link, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        editor.removeLink(id, link);
        return "redirect:/betreuende/profilEdit";
    }
}