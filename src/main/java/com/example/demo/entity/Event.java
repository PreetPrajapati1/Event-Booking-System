package com.example.demo.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String eventName;
    private Date eventDate;

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public Date getEventDate() { return eventDate; }
    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }
}
