package dev.group3.model;

import dev.group3.model.enums.userType;

public class User {
    
    private String email;
    private String pswd;
    private String firstName;
    private String lastName;
    private userType userType;
    private String phoneNumber;
    private Double funds;
    
    public User() {}

    public User(String email, String pswd, String firstName, String lastName, dev.group3.model.enums.userType userType,
            String phoneNumber, Double funds) {
        super();
        this.email = email;
        this.pswd = pswd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.phoneNumber = phoneNumber;
        this.funds = funds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public userType getUserType() {
        return userType;
    }

    public void setUserType(userType userType) {
        this.userType = userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getFunds() {
        return funds;
    }

    public void setFunds(Double funds) {
        this.funds = funds;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", pswd=" + pswd + ", firstName=" + firstName + ", lastName=" + lastName
                + ", userType=" + userType + ", phoneNumber=" + phoneNumber + ", funds=" + funds + "]";
    }
}
