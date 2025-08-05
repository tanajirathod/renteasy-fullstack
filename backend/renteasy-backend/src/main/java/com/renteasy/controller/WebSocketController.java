/*package com.renteasy.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/likes")
    @SendTo("/topic/likes")
    public String handleLike(String message) {
        return message;
    }
}
*/