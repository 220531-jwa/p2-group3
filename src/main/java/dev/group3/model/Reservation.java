package dev.group3.model;

import java.sql.Timestamp;

import dev.group3.model.enums.ResStatusType;

public class Reservation {

    private Integer id;
    private String userEmail;
    private Integer dogId;
    private ResStatusType status;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    
    public Reservation() {}

    public Reservation(Integer id, String userEmail, Integer dogId, ResStatusType status, Timestamp startDateTime,
            Timestamp endDateTime) {
        super();
        this.id = id;
        this.userEmail = userEmail;
        this.dogId = dogId;
        this.status = status;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Integer getId() {
        return id;
    }

    public Reservation setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Reservation setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public Integer getDogId() {
        return dogId;
    }

    public Reservation setDogId(Integer dogId) {
        this.dogId = dogId;
        return this;
    }

    public ResStatusType getStatus() {
        return status;
    }

    public Reservation setStatus(ResStatusType status) {
        this.status = status;
        return this;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public Reservation setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public Reservation setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    @Override
    public String toString() {
        return "Reservation [id=" + id + ", userEmail=" + userEmail + ", dogId=" + dogId + ", status=" + status
                + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + "]";
    }
}
