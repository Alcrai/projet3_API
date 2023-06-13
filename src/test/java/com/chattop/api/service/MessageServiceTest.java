package com.chattop.api.service;

import com.chattop.api.entity.Message;
import com.chattop.api.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;

    private  MessageService messageService;
    private Message message;

    @BeforeEach
    void init(){
        messageService = new MessageService(messageRepository);
        message = new Message(1,1,1,"Dispo le 20 juin pour louer?", Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    void findAll() {
        List<Message> messages =  new ArrayList<>();
        messages.add(message);
        when(messageRepository.findAll()).thenReturn(messages);
        assertThat(messageService.findAll()).hasSize(1);
        verify(messageRepository).findAll();
    }

    @Test
    void findById() {
        when(messageRepository.findById(any(Integer.class))).thenReturn(Optional.of(message));
        assertThat(messageService.findById(1)).isEqualTo(message);
        verify(messageRepository).findById(any(Integer.class));
    }

    @Test
    void add() {
        Message message2 = new Message(2,1,1,"une ristourne",Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        when(messageRepository.save(any(Message.class))).thenReturn(message2);
        assertThat(messageService.add(message2)).isEqualTo(message2);
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void update() {
        Message message2 = new Message(1,1,1,"une ristourne",Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        assertThat(messageService.update(message2)).isEqualTo(message);
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void deleteIdExist() {
        when(messageRepository.existsById(any(Integer.class))).thenReturn(true);
        assertThat(messageService.delete(message)).isEqualTo(message);
        verify(messageRepository).existsById(any(Integer.class));
        verify(messageRepository).delete(any(Message.class));
    }

    @Test
    void deleteIdNotExist() {
        when(messageRepository.existsById(any(Integer.class))).thenReturn(false);
        assertThat(messageService.delete(message)).isEqualTo(message);
        verify(messageRepository).existsById(any(Integer.class));
        verify(messageRepository,times(0)).delete(any(Message.class));
    }
}