package com.chattop.api.service;

import com.chattop.api.entity.User;
import com.chattop.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;
    private User user;

    @BeforeEach
    void init(){
        userService = new UserService(userRepository);
        user = new User(1,"test@test.com","Test TEST","test",Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    void findAll() {
        List<User> users =  new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        assertThat(userService.findAll()).hasSize(1);
        verify(userRepository).findAll();
    }

    @Test
    void findById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        assertThat(userService.findById(1)).isEqualTo(user);
        verify(userRepository).findById(1);
    }

    @Test
    void add() {
        User user2 =  new User(2,"test2@test.com","Test TEST","test", Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        when(userRepository.save(any(User.class))).thenReturn(user2);
        assertThat(userService.add(user2)).isEqualTo(user2);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void update() {
        User user2 =  new User(1,"test2@test.com","Test TEST","test",Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertThat(userService.update(user2)).isEqualTo(user);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteIdExist() {
        when(userRepository.existsById(any(Integer.class))).thenReturn(true);
        assertThat(userService.delete(user)).isEqualTo(user);
        verify(userRepository).existsById(any(Integer.class));
        verify(userRepository).delete(any(User.class));
    }

    @Test
    void deleteIdNotExist() {
        when(userRepository.existsById(any(Integer.class))).thenReturn(false);
        assertThat(userService.delete(user)).isEqualTo(user);
        verify(userRepository).existsById(any(Integer.class));
        verify(userRepository,times(0)).delete(any(User.class));
    }

    @Test
    void create() {
        assertThat(userService.create(user).getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        assertThat(userService.findByEmail("test@test.com")).isEqualTo(user);
        verify(userRepository).findByEmail(any(String.class));
    }
}