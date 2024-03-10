package com.fatec.quintosemestre.projetomarketing.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.fatec.quintosemestre.projetomarketing.service.MyOpenAiService;

@Controller
public class WebSocketController {

    @Autowired
    private MyOpenAiService openAiService;

    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public Object handleMessage(@DestinationVariable String chatId, @RequestBody Map<String, Object> object) throws Exception {
        Map<String, Object> result = new HashMap<>();
        String response = openAiService.responder((String) object.get("message"));
        result.put("message", response);
        result.put("from", "professional");
        return result;
    }

}
