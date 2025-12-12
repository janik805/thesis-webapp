package com.awesome.thesis.controller.admin;

import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ThemaEditorController {

    @Autowired
    ThemaEditor editor;

    @GetMapping("/editThema/{id}")
    public String editThema(@PathVariable("id")String id, Model model) {
        model.addAttribute("thema", editor.getThema(id));
        return "admin/themaEdit";
    }
}
