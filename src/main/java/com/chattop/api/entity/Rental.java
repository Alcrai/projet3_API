package com.chattop.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "RENTALS")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="surface")
    private float surface;
    @Column(name="price")
    private float price;
    @Column(name="picture")
    private String picture;
    @Column(name="description")
    private String description;
    @Column(name="owner_id")
    private int owner_id;
    @Column(name = "created_at")
    private Timestamp created_at;
    @Column(name = "updated_at")
    private Timestamp updated_at;

    public Rental() {
    }

    public Rental(int id, String name, float surface, float price, String picture, String description, int owner_id, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.owner_id = owner_id;
        this.created_at = createdAt;
        this.updated_at = updatedAt;
    }
}
