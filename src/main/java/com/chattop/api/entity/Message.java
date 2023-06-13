package com.chattop.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "MESSAGES")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="rental_id")
    private int rental_id;
    @Column(name="user_id")
    private int user_id;
    @Column(name="message")
    private String message;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;

    public Message() {
    }

    public Message(int id, int rental_id, int user_id, String message, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.rental_id = rental_id;
        this.user_id = user_id;
        this.message = message;
        this.created_at = createdAt;
        this.updated_at = updatedAt;
    }
}
