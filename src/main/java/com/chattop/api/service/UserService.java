package com.chattop.api.service;

import com.chattop.api.entity.User;
import com.chattop.api.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user){
        User userCreate= new User();
        userCreate.setEmail(user.getEmail());
        userCreate.setName(user.getName());
        userCreate.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userCreate.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        userCreate.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        return userCreate;
    }
    public List<User> findAll(){
        log.info("List of USERS");
        return userRepository.findAll();
    }

    public User findById(int id){
        Optional<User> result = userRepository.findById(id);
        log.info("View user by Id:" + id);
        return result.get();
    }

    public User add(User user){
        log.info("Add user:" + user.toString());
        return userRepository.save(user);
    }

    public User update(User user){
        return userRepository.save(user);
    }

    public User delete(User user){
        if (userRepository.existsById(user.getId())){
            userRepository.delete(user);
            log.info("deleted user :" + user.toString());
        } else {
            log.error("user does not exist");
        }
        return user;
    }

    public User findByEmail(String subject) {
        return userRepository.findByEmail(subject).get();
    }
}
