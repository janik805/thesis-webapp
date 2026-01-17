package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.controller.dto.ThemaInfoDTO;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Secured("ROLE_BETREUENDE")
public class BetreuendeThemaCreateController {

    @Autowired
    ThemaEditor themaEditor;

    @Autowired
    ProfilEditor profilEditor;

    @GetMapping("/thema/create")
    public String getSite( Model model) {
        model.addAttribute(new ThemaInfoDTO("", ""));
        return "betreuende/themaCreate";
    }

    @PostMapping("/thema/create")
    public String postThema(RedirectAttributes redirect, @Valid @ModelAttribute("themaInfoDTO")ThemaInfoDTO dto, BindingResult result, OAuth2AuthenticationToken auth) {
        if(result.hasErrors()) {
            return "betreuende/themaCreate";
        }
        Integer profilID = auth.getPrincipal().getAttribute("id");
        Thema thema = new Thema(dto.titel(), profilID);
        themaEditor.addThema(thema, profilID);
        Integer themaId = thema.getId();
        themaEditor.editBeschreibung(themaId, dto.beschreibung());
        redirect.addFlashAttribute("themaInfoDTO", dto);
        return "redirect:/themaEdit/" + themaId;
    }
}
