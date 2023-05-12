package org.medical.hub.marker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/marker-page")
public class MarkerController {
    @GetMapping()
    public String chat() {

        return "marker/index";
    }

    @GetMapping("/create")
    public String markerCreate() {

        return "marker/create";
    }

    @GetMapping("/view-template")
    public String viewTemplate(@RequestParam() String tag, Model model) {
        model.addAttribute("tag", tag);
        return "marker/viewTemplate";
    }
    @GetMapping("/template/edit")
    public String edit(@RequestParam() String tag, Model model) {
        model.addAttribute("tag", tag);
        return "marker/edit";
    }
    @GetMapping("/view")
    public String markerView() {
        return "marker/view";
    }
}
