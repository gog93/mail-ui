package org.medical.hub.marker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping("/view")
    public String markerView() {

        return "marker/view";
    }
}
