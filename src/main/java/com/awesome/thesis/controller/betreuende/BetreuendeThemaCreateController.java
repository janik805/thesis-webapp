package com.awesome.thesis.controller.betreuende;

import com.awesome.thesis.controller.dto.ThemaInfoDTO;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BetreuendeThemaCreateController {

    @GetMapping("/thema/create")
    public String getSite( Model model) {
        model.addAttribute(new ThemaInfoDTO("", ""));
        return "betreuende/themaCreate";
    }
}
