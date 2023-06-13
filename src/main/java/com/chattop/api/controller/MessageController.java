package com.chattop.api.controller;

import com.chattop.api.entity.Message;
import com.chattop.api.entity.MessageResponse;
import com.chattop.api.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/api/messages")
    public ResponseEntity<MessageResponse> postMessage(@RequestBody Message message){
        Message messageAdded = messageService.add(messageService.create(message));
        if (messageAdded.getId()==0){
            log.error("failed to save Message");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("failed to save Message"));
        }
        log.info("message created:" + messageAdded.toString());
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Message send with success"));
    }
}
