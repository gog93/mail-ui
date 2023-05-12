package org.medical.hub.marker.controller;

import lombok.RequiredArgsConstructor;
import org.medical.hub.marker.service.AllTamplatesService;
import org.medical.hub.marker.service.ChatServiceImpl;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/edit-template")
    public String edit(@RequestParam(name = "search", required = false) String search) throws InterruptedException {
        if (!search.startsWith("@Edit")){
            return search;
        }
        chatService.write(search);
        return chatService.read();
    }

    @GetMapping("/view/template/{tag}")
    public String getTemplate(@PathVariable("tag") String tag){

        return tamplatesService.findTemplateByTag(tag);
    }
    @DeleteMapping("/view/delete-template/{tag}")
    public String deleteTemplate(@PathVariable("tag") String tag){

        return tamplatesService.deleteTemplateByTag(tag);
    }

    @GetMapping("/template-list")
    public String allTemplate() {

        return tamplatesService.getQueryApi();
    }
}
