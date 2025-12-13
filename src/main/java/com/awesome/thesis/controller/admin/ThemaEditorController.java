package com.awesome.thesis.controller.admin;
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
        model.addAttribute("themaLinkDTO", new ThemaLinkDTO("", ""));
        model.addAttribute("thema", thema);
        return "admin/themaEdit";
    }

    @PostMapping("themaEdit/{id}/editInfo")
    public String editThemaInfo(@PathVariable String id, @RequestParam String titel, @RequestParam(defaultValue="") String beschreibung) {
        editor.editTitel(id,titel);
        editor.editBeschreibung(id, beschreibung);
        return "redirect:/editThema/" + id;
    }

    @PostMapping("/themaEdit/{id}/editLink")
    public String editThemaLink(@PathVariable String id, @ModelAttribute("themaLinkDTO")ThemaLinkDTO dto) {
        editor.addLink(id, dto.url(), dto.urlBeschreibung());
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
