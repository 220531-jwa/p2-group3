# Doggy Daycare System (DDS)

## Project Description

The Doggy Daycare System (DDS) manages the process of customers booking and tracking reservations and services for their furry friends at the Doggy Daycare. Once a customer has created their account they have the ability to login, update their personal information, register their dog(s), update their dog information, book a new reservation and services for a dog, view all past/current reservations and services, as well as cancel a reservation if needed. The owner has the ability to login and view all customers, dogs, reservations and check-in/check-out the dogs upon arrival/departure. 

## Technologies Used

* Java - version 8.0
* HTML 
* JavaScript 
* AWS RDS
* CSS
* Maven
* Mockito
* Bootstrap
* PostgreSQL
* Selenium
* Javalin
* JUnit
* JDBC


## Features

Implementedd Features:

* A user can login & logout of their account
* A user can create an account
* A user can view their account information
* A user can edit their account information
* A user can register a dog to their account
* A user can view their dog information
* A user can edit their dog information
* A user can delete their dog information
* A user can create a reservation to drop their dog off
* A user can view their reservation
* A user can cancel their reservation
* The owner can see any user account information
* The owner can see any dog information
* The owner can see any reservation information
* The owner can checkin and checkout the dog

To-do & Improvements list:

* User can change reservation drop-off
* Owner can hire employees (Different user type)
* Dogs can stay for more granular lengths of time (Currently 1 hour groups)
* Customer Reward System
* Owner can change price of hourly rate and dog services
* Users can have more than 1 dog service for their dog
* Owner can deal with 10 dogs at any given time (instead of for the day)
* Transactions:
   * The owner can see all transactions for his business
   * A user can see their transaction history

## Getting Started

Project Setup:

* Clone project onto machine
* Add project to eclipse as a maven project
* Add connection.properties file into src/main/resources
   * Valuess: {postgresql driver, database (DB), url, db username, db password}
   * Dabase used was AWS
   
Database setup:

* Connect to database via DeBeaver
* Run db setup sql file (for tabbles and initial data set)

Start Program:

* Run driver from eclipse

## Usage

* Open you're prefered web browser.
   * Chrome was used
   * Startingpoint is .../login.html
* Create an account that you want to use
   * Or login with an existing account
* Home Page:
   * Display and edit your user profile account information by clicking the "Edit UserProfile" tab on the side nav
   * View Reservation information by clicking on the "Reservations" tab on the side nav
   * View Your Dogs information by clicking on the "Dogs" tab on the side nav
   * Logout by clicking "logout" on the top nav on the right
* UserProfile:
   * When Creating or editing user information:
      * the email must be of the form prefix@domain.com
      * the password must be at least 8 characters long with 1 upper, lower, number, and symbol
      * firstname and lastname must not be blank
      * Phonenumber needs 10 digits
      * funds must be within 0-10,000.00
   * Click save/submit depending on whether you are creating or editing your account
* Reservations:
   * If the index does not show it or work: You can manually go to .../newReservation.html or .../editReservation.html or .../ReservationPage.html
   * To see all your resevations click the "View ALl Your Resevations" Button
   * To register for a new reservation click the "Book New Reservation" button
      * After selecting a dog, start/end drop off, and any optional service for your dog, click submit
   * To edit a reservation, on the side of your reservation click "edit"
* Dogs:
   * If you index does not show it or work: You can manually go to .../DogsPage.html
   * To see all your dogs click "View ALl Your Dogs" button
   * TO regitser a a dog to your account click "Bookt New Dog" button
      * All fields are required
   * To edit a dogs information click the "edit" button on the side of the record

## Contributors

> Joshua Friesner  
> Gerard Prats  
> Theresa Sherman  
> Robert Randolph  
