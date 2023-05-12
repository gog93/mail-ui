package org.medical.hub.marker.controller;

import lombok.RequiredArgsConstructor;
import org.medical.hub.marker.service.AllTamplatesService;
import org.medical.hub.marker.service.ChatServiceImpl;
//import org.medical.hub.marker.service.Demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marker")
public class ChatController {
//    private final Demo demo;

    private final ChatServiceImpl chatService;
    private final AllTamplatesService tamplatesService;

    @GetMapping()
    public String chat(@RequestParam(name = "search", required = false) String search) throws InterruptedException {
        chatService.write(search);

        return chatService.read();
    }
    @GetMapping("/template-list")
    public String allTamplate() {

        return tamplatesService.getQueryApi();
    }
}
