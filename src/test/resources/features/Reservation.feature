#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Reservation Portal

	Background:
	Given The User or Owner has already logged in



	# Check correct html routing and DOM manipulation
  Scenario: User should land on ReservationsPage
    Given User has successfully logged in
    And The User is on their HomePage
    When The User clicks on Reservations button
    Then The User should be on the ReservationsPage
    And The AllReservations table should be visible
    
    
    
#----------------------------------------------------------  
	
# TESTING THE TOP TOGGLE BUTTONS
	
	# TESTING VIEW ALL REQUESTS BUTTON
	Scenario: A User can see all of their Reservations
    Given a User is on the ReservationsPage
    When User clicks ViewAllReservations button
    Then AllReservations Table is visible
    And NewReservation Form is not visible
    
    
  # TESTING VIEW ADD DOGS BUTTON
  Scenario: 
    Given User is on ReservationsPage
    When User clicks NewReservation button
    Then The NewReservation Form should be visible
    And The AllReservations Table should not be visible
    
    
    
#--------------------------------------------------------------------------



################# THIS IS ALL FOR THE CLIENT USER SIDE #####################


# TESTING OUT ALL RESERVATIONS 

	# TEST TO MAKE SURE USER CAN PULL UP A SINGLE RESERVATIONS
	 Scenario: A User can see one of their Reservations
	 
    Given A User is on the Reservations Page
    And The User is viewing the AllReservations Table
    When The User clicks the ViewReservation button
    Then The ViewAndEditReservation from should be visible
    And The AllReservationsTable should not be visible
    
    
 # TESTS TO MAKE SURE WHEN EDIT BUTTON CLICKED THEN THE FIELDS ARE EDITABLE
  Scenario: A User can edit a Reservation
    Given A User is on the Reservations Page
    And The User has clicked EditReservation button
    And The EditReservationForm is visible
    When The User clicks on EditReservationButton
    Then The EditReservationForm fields are editable
    And The Submit button is visible
    And The EditReservationButton is not visible
    
    
    # TESTS TO MAKE SURE WHEN SUBMIT BUTTON CLICKED THEN THE UPDATE IS HANDLED APPROPRIATLY
   Scenario: A User can Submit their Reservation Edits
    Given A User is on the Reservations Page
    And The User has the clicked EditReservation button
    And The User has edited the reservation status
    When The User clicks Submit
    Then All thed fields on EditReservationForm are disabled
    And A message confirming update appears above EditReservationForm
    
    
#----------------------------------------------------------

################# THIS IS ALL FOR THE OWNER USER SIDE #####################


# TESTING OUT ALL RESERVATIONS 

	# TEST TO MAKE SURE USER CAN PULL UP A SINGLE RESERVATIONS
	 Scenario: The Owner User can see one of their Reservations
	 
    Given The Owner is on the Reservations Page
    And The Owner is viewing the AllReservations Table
    When The Owner clicks the ViewReservation button
    Then The ViewAndEditReservation from should be visible
    And The AllReservationsTable should not be visible
    
    
 # TESTS TO MAKE SURE WHEN EDIT BUTTON CLICKED THEN THE FIELDS ARE EDITABLE
  Scenario: The Owner can edit a Reservation
    Given The Owner is on the Reservations Page
    And The Owner has clicked EditReservation button
    And The EditReservationForm is visible
    When The Owner clicks on EditReservationButton
    Then The EditReservationForm fields are editable
    And The Submit button is visible
    And The EditReservationButton is not visible
    
    
    # TESTS TO MAKE SURE WHEN SUBMIT BUTTON CLICKED THEN THE UPDATE IS HANDLED APPROPRIATLY
   Scenario: The Owner can Submit their Reservation Edits
    Given The Owner is on the Reservations Page
    And The Owner has the clicked EditReservation button
    And The Owner has edited the reservation status
    When The Owner clicks Submit
    Then All the fields on EditReservationForm are disabled
    And A message confirming update appears above EditReservationForm
    
    
    
    
    
##################################################################################################




# CREATE NEW REQUEST TESTING

	# TESTS TO MAKE SURE THE CREATE NEW REQUEST IS HANDLED CORRECLTY
  Scenario: The User can submit a new request
    Given The User is on the ReservationsPage
    And The User has clicked NewReservation button
    When The User completes the NewReservation form
    And The User clicks submit
    Then The AllReservationsTable should be visible
    And The NewReservation should be in the table
    And A confirmation message above AllReservationsTable should be visible
    



    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
   
