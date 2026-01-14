package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.controller.dto.FachgebietDTO;
import com.awesome.thesis.controller.dto.LinkDTO;
import com.awesome.thesis.controller.dto.ProfilEditDTO;
import com.awesome.thesis.controller.dto.kontakt.EmailKontaktDTO;
import com.awesome.thesis.controller.dto.kontakt.TelKontaktDTO;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.profil.Kontakt;
import com.awesome.thesis.logic.domain.model.profil.ProfilLink;
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
        model.addAttribute("profilEditDTO", new ProfilEditDTO(""));
        model.addAttribute("fachgebietDTO", new FachgebietDTO(""));
        return "betreuende/profilEdit";
    }

    @PostMapping("profilEdit")
    public String profilEdit(@Valid @ModelAttribute() ProfilEditDTO profilEditDTO, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        if  (result.hasErrors()) {
            model.addAttribute("profil", editor.get(id));
            model.addAttribute("kontakt", new EmailKontaktDTO("email","", ""));
            model.addAttribute("linkDTO", new LinkDTO("", ""));
            model.addAttribute("fachgebietDTO", new FachgebietDTO(""));
            return "betreuende/profilEdit";
        }
        editor.editName(id, profilEditDTO.name());
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
            model.addAttribute("profilEditDTO", new ProfilEditDTO(""));
            model.addAttribute("fachgebietDTO", new FachgebietDTO(""));
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
            model.addAttribute("profilEditDTO", new ProfilEditDTO(""));
            model.addAttribute("fachgebietDTO", new FachgebietDTO(""));
            return "betreuende/profilEdit";
        }
        editor.addTel(id, tel.label(), tel.wert());
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping("profilEdit/addFachgebiet")
    public String addFachgebiet(@Valid @ModelAttribute() FachgebietDTO fachgebietDTO, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        if (result.hasErrors()) {
            model.addAttribute("profil", editor.get(id));
            model.addAttribute("kontakt", new EmailKontaktDTO("email","", ""));
            model.addAttribute("linkDTO", new LinkDTO("", ""));
            model.addAttribute("profilEditDTO", new ProfilEditDTO(""));
            return "betreuende/profilEdit";
        }
        editor.addFachgebiet(id, fachgebietDTO.fachgebiet());
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping("profilEdit/removeFachgebiet")
    public String removeFachgebiet(String fachgebiet, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        editor.removeFachgebiet(id, fachgebiet);
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping("/profilEdit/addLink")
    public String editThemaLink(@Valid @ModelAttribute() LinkDTO linkDTO, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        if (result.hasErrors()){
            model.addAttribute("profil", editor.get(id));
            model.addAttribute("kontakt", new EmailKontaktDTO("email","", ""));
            model.addAttribute("profilEditDTO", new ProfilEditDTO(""));
            model.addAttribute("fachgebietDTO", new FachgebietDTO(""));
            return "betreuende/profilEdit";
        }
        editor.addLink(id, linkDTO.url(), linkDTO.urlBeschreibung());
        return "redirect:/betreuende/profilEdit";
    }

    @PostMapping("/profilEdit/deleteLink")
    public String deleteLink(@ModelAttribute ProfilLink link, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        editor.removeLink(id, link);
        return "redirect:/betreuende/profilEdit";
    }
}