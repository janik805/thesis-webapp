package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.controller.dto.EmailKontaktDTO;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
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
public class AdminProfilController {
    @Autowired
    ProfilEditor editor;

    @GetMapping("profilEdit")
    public String profilEdit(Model model, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        model.addAttribute("profil", editor.get(id));
        model.addAttribute("email", new EmailKontaktDTO("", ""));
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

    @PostMapping("profilEdit/addEmail")
    public String addEmail(@Valid @ModelAttribute("email") EmailKontaktDTO email, BindingResult result, Model model, OAuth2AuthenticationToken auth) {
        Integer id = auth.getPrincipal().getAttribute("id");
        if  (result.hasErrors()) {
            model.addAttribute("profil", editor.get(id));
            return "betreuende/profilEdit";
        }
        editor.addEmail(id, email.label(), email.wert());
        return "redirect:/betreuende/profilEdit";
    }
}
