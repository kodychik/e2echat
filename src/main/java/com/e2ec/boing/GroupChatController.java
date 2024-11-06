package com.e2ec.boing;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GroupChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public GroupChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(HelloMessage message) {
        // The group code is used as part of the destination topic
        String destination = "/topic/group/" + HtmlUtils.htmlEscape(message.getGroupCode());
        // Send message to all subscribers of this group
        messagingTemplate.convertAndSend(destination, new Greeting(message.getName() + ": " + message.getContent()));
    }
}
