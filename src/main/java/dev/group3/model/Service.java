package dev.group3.model;

import dev.group3.model.enums.ServiceType;

public class Service {
    
    private Integer id;
    private ServiceType serviceType;
    private Integer durationHour;
    private Double price;
    
    public Service() {}

    public Service(Integer id, ServiceType serviceType, Integer durationHour, Double price) {
        super();
        this.id = id;
        this.serviceType = serviceType;
        this.durationHour = durationHour;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public Service setId(Integer id) {
        this.id = id;
        return this;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public Service setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public Integer getDurationHour() {
        return durationHour;
    }

    public Service setDurationHour(Integer durationHour) {
        this.durationHour = durationHour;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Service setPrice(Double price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "Service [id=" + id + ", serviceType=" + serviceType + ", durationHour=" + durationHour + ", price="
                + price + "]";
    }
}
