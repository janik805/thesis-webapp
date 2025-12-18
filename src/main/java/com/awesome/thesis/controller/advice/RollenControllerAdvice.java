package com.awesome.thesis.controller.advice;

import com.awesome.thesis.controller.StartController;
import com.awesome.thesis.controller.dto.NavbarButtonDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice()
public class RollenControllerAdvice {
    @ModelAttribute("navButton")
    public NavbarButtonDTO getNavButton(Authentication token) {
        if(!(token instanceof OAuth2AuthenticationToken auth)) {
            return new NavbarButtonDTO("Anmelden", "/login");
        }
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return new NavbarButtonDTO("Admin", "/admin");
        }
        boolean isBetreuende = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_BETREUENDE"));
        String name = auth.getPrincipal().getAttribute("login");
        if (isBetreuende) {
            return new NavbarButtonDTO(name, "/betreuende");
        }
        return new NavbarButtonDTO(name, "#");
    }
}
