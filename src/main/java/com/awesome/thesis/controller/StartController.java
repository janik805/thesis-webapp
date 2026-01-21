package com.awesome.thesis.controller;

import com.awesome.thesis.controller.dto.ProfilFooterDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class StartController {
    @ModelAttribute("profilBanner")
    public ProfilFooterDto getBanner(Authentication token) {
        if(!(token instanceof OAuth2AuthenticationToken auth)) {
            return new ProfilFooterDto("Fange jetzt an!", "Melde Dich an und finde eine:n Betreuer:in sowie ein Thema für Deine Abschlussarbeit!", "/login", "Anmelden");
        }
        boolean isBetreuende = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_BETREUENDE"));
        String name = auth.getPrincipal().getAttribute("login");
        String greeting = "Hallo " + name + "!";
        if (isBetreuende) {
            return new ProfilFooterDto(greeting, "Bearbeite Dein Profil, füge Themen hinzu und unterstütze Studierende dabei, ihre Abschlussarbeit zu finden!", "/betreuende/profilEdit", "Profil bearbeiten");
        }
        return new ProfilFooterDto(greeting, "Füge Deine Interessen und bestandene Module hinzu und finde ein:n Betreuer:in sowie ein Thema für Deine Abschlussarbeit!", null, null);
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
