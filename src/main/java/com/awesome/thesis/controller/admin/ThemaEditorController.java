package com.awesome.thesis.controller.admin;

import com.awesome.thesis.controller.dto.ThemaInfoDTO;
import com.awesome.thesis.controller.dto.ThemaLinkDTO;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ThemaEditorController {

    @Autowired
    ThemaEditor editor;

    @GetMapping("/editThema/{id}")
    public String editThema(@PathVariable("id")String id, Model model) {
        Thema thema = editor.getThema(id);
        ThemaInfoDTO themaInfoDTO = new ThemaInfoDTO(thema.getTitel(), thema.getBeschreibung());
        model.addAttribute("themaLinkDTO", new ThemaLinkDTO("", ""));
        model.addAttribute("themaInfoDTO", themaInfoDTO);
        model.addAttribute("thema", thema);
        return "admin/themaEdit";
    }

    @PostMapping("themaEdit/{id}/editInfo")
    public String editThemaInfo(@PathVariable String id, @ModelAttribute("themaInfoDTO")ThemaInfoDTO dto) {
        editor.editTitel(id, dto.titel());
        editor.editBeschreibung(id, dto.beschreibung());
        return "redirect:/editThema/" + id;
    }

    @PostMapping("/themaEdit/{id}/editLink")
    public String editThemaLink(@PathVariable String id, @ModelAttribute("themaLinkDTO")ThemaLinkDTO dto) {
        editor.addLink(id, new Link(dto.url(), dto.urlBeschreibung()));
        return "redirect:/editThema/" + id;
    }

    @PostMapping("/themaEdit/{id}/deleteLink")
    public String deleteLink(@ModelAttribute Link link, @PathVariable String id, @ModelAttribute("themaLinkDTO")ThemaLinkDTO dto) {
        editor.removeLink(id, link);
        return "redirect:/editThema/" + id;
    }

    @PostMapping("/themaAnsicht/{id}")
    public String returnToThema(@PathVariable String id) {
        return "redirect:/thema/" + id;
    }


}
