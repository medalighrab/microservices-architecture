package com.microservice.event.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter


@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String title;
    private String location;
    private String date;
    private Integer userId;

    // 🔹 Constructeur par défaut
    public Event() {}

    // 🔹 Constructeur avec tous les champs
    public Event(Long id, String title, String location, String date) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
    }

    // 🔹 Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // 🔹 Méthode pour faciliter l'affichage en Map (optionnel)
    public java.util.Map<String, Object> toMap() {
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("id", this.id);
        map.put("title", this.title);
        map.put("location", this.location);
        map.put("date", this.date);
        return map;
    }
}
