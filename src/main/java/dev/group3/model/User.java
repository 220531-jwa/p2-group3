package dev.group3.model;

import dev.group3.model.enums.UserType;

public class User {
    
    private String email;
    private String pswd;
    private UserType userType;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Double funds;
    
    public User() {}

    public User(String email, String pswd, UserType userType, String firstName, String lastName, String phoneNumber,
            Double funds) {
        super();
        this.email = email;
        this.pswd = pswd;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.funds = funds;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPswd() {
        return pswd;
    }

    public User setPswd(String pswd) {
        this.pswd = pswd;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public User setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Double getFunds() {
        return funds;
    }

    public User setFunds(Double funds) {
        this.funds = funds;
        return this;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", pswd=" + pswd + ", userType=" + userType + ", firstName=" + firstName
                + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", funds=" + funds + "]";
    }
}
