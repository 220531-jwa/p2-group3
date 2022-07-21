package dev.group3.model.DTO;

import dev.group3.model.Reservation;
import dev.group3.model.enums.ServiceType;

public class ReservationDTO {
    
    private String userFirstName;
    private String userLastName;
    private Reservation reservation;
    private String dogName;
    private ServiceType service;
    
    public ReservationDTO() {}

    public ReservationDTO(String userFirstName, String userLastName, Reservation reservation, String dogName,
            ServiceType service) {
        super();
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.reservation = reservation;
        this.dogName = dogName;
        this.service = service;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public ReservationDTO setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
        return this;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public ReservationDTO setUserLastName(String userLastName) {
        this.userLastName = userLastName;
        return this;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public ReservationDTO setReservation(Reservation reservation) {
        this.reservation = reservation;
        return this;
    }

    public String getDogName() {
        return dogName;
    }

    public ReservationDTO setDogName(String dogName) {
        this.dogName = dogName;
        return this;
    }

    public ServiceType getService() {
        return service;
    }

    public ReservationDTO setService(ServiceType service) {
        this.service = service;
        return this;
    }

    @Override
    public String toString() {
        return "ReservationDTO [userFirstName=" + userFirstName + ", userLastName=" + userLastName + ", reservation="
                + reservation + ", dogName=" + dogName + ", service=" + service + "]";
    }
}
