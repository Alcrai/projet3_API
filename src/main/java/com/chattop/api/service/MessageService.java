package com.chattop.api.service;

import com.chattop.api.entity.Message;
import com.chattop.api.entity.Rental;
import com.chattop.api.repository.MessageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class MessageService {
    private MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAll(){
        log.info("List of Message");
        return messageRepository.findAll();
    }

    public Message findById(int id){
        Optional<Message> result = messageRepository.findById(id);
        log.info("View Message by Id:" + id);
        return result.get();
    }

    public Message add(Message message){
        log.info("Add Message:" + message.toString());
        return messageRepository.save(message);
    }

    public Message update(Message message){
        return messageRepository.save(message);
    }

    public Message delete(Message message){
        if (messageRepository.existsById(message.getId())){
            messageRepository.delete(message);
            log.info("deleted Message :" + message.toString());
        } else {
            log.error("Message does not exist");
        }
        return message;
    }


    public Message create(Message message) {
        Message result = new Message();
        result.setRental_id(message.getRental_id());
        result.setUser_id(message.getRental_id());
        result.setMessage(message.getMessage());
        result.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        result.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        return result;
    }
}
