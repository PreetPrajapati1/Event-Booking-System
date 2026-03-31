package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String seatRow;
    private int seatNumber;
    private boolean isBooked;
    private Integer bookedBy;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSeatRow() { return seatRow; }
    public void setSeatRow(String seatRow) { this.seatRow = seatRow; }

    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }

    public boolean isBooked() { return isBooked; }
    public void setBooked(boolean booked) { isBooked = booked; }

    public Integer getBookedBy() { return bookedBy; }
    public void setBookedBy(Integer bookedBy) { this.bookedBy = bookedBy; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
}
