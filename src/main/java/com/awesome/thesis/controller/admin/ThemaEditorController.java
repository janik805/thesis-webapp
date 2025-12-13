package com.awesome.thesis.controller.admin;
import com.awesome.thesis.controller.dto.ThemaInfoDTO;
import com.awesome.thesis.controller.dto.ThemaLinkDTO;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.links.Link;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ThemaEditorController {

    @Autowired
    ThemaEditor editor;

    @GetMapping("/themaEdit/{id}")
    public String editThema(@PathVariable("id")String id, Model model) {
        model.addAttribute("themaLinkDTO", new ThemaLinkDTO("", ""));
        model.addAttribute("themaInfoDTO", editor.getThemaInfoDTO(id));
        model.addAttribute("thema", editor.getThema(id));
        return "admin/themaEdit";
    }

    @PostMapping("themaEdit/{id}/editInfo")
    public String editThemaInfo(@PathVariable String id,@Valid @ModelAttribute("themaInfoDTO") ThemaInfoDTO themaInfoDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("themaLinkDTO", new ThemaLinkDTO("", ""));
            model.addAttribute("thema", editor.getThema(id));
            return "admin/themaEdit";
        }
        editor.editTitel(id,themaInfoDTO.titel());
        editor.editBeschreibung(id, themaInfoDTO.beschreibung());
        return "redirect:/themaEdit/" + id;
    }

    @PostMapping("/themaEdit/{id}/editLink")
    public String editThemaLink(@PathVariable String id, @Valid @ModelAttribute("themaLinkDTO")ThemaLinkDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("themaInfoDTO", editor.getThemaInfoDTO(id));
            model.addAttribute("thema", editor.getThema(id));
            return "admin/themaEdit";
        }
        editor.addLink(id, dto.url(), dto.urlBeschreibung());
        return "redirect:/themaEdit/" + id;
    }

    @PostMapping("/themaEdit/{id}/deleteLink")
    public String deleteLink(@ModelAttribute Link link, @PathVariable String id, @ModelAttribute("themaLinkDTO")ThemaLinkDTO dto) {
        editor.removeLink(id, link);
        return "redirect:/themaEdit/" + id;
    }

    @PostMapping("/themaAnsicht/{id}")
    public String returnToThema(@PathVariable String id) {
        return "redirect:/thema/" + id;
    }


}
