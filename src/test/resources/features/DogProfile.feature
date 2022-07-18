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




Feature: Dog Profile Page



	Background:
		Given The User or Owner has already logged in


	
	# Check correct html routing and DOM manipulation
  Scenario: User Navigates to MyDogsPage
    When The User clicks on MyDogs buttons
    Then The User should be on the MyDogsPage
    And The AllDogsTable should be visible
    

#----------------------------------------------------------  
	
# TESTING THE TOP TOGGLE BUTTONS
	
	# TESTING VIEW ALL DOGS BUTTON
	Scenario: A User can see all of their dogs
    Given A User is on the DogProfilePage
    When The User clicks ViewAllDogsButton
    Then The AllDogsTable should be visible
    And The NewDogForm should not be visible 
    
    
  # TESTING VIEW ADD DOGS BUTTON
  Scenario: 
    Given A User is on DogProfilePage
    When The User clicks AddNewDogButton
    Then The NewDogForm is visible
    And The ViewAllDogsTable should not be visible
    
    
    
    
 
 #----------------------------------------------------------  
 
 
 
 
# TESTING VIEW DOGS PROFILE

	# TEST TO MAKE SURE DOG PROFILE IS VISIBLE AND TABLE IS NOT
  Scenario: A User can see one of their dogs profiles
    Given A User is on the Dog Profile Page
    And A user has clicked View All Dogs button
    When The User clicks on their dog
    Then The clicked dogs profile is visible
    And The AllDogsTable is not visible


	# TESTS TO MAKE SURE WHEN EDIT BUTTON CLICK FIELDS ARE EDITABLE
  Scenario: A User can edit Dogs Profile
    Given A User is on the Dog Profile Page
    And A user has clicked View All Dogs button
    And The User has clicked one of their dogs
    When The User clicks on EditDogProfileButton
    Then The clicked dogs profile fields are editable
    
    
   # TESTS TO MAKE SURE DOG UPDATING IS HANDLED CORRECTLY
   Scenario: A User can see one of their dogs
    Given A User is on the Dog Profile Page
    And A user has clicked View All Dogs button
    And The User has clicked one of their dogs
    And The User has clicked on Edit Dog Profile
    And The User has updated a field
    When The User clicks Submit
    Then All the fields on DogProfileForm are disabled
    And A message confirming update appears above DogProfileForm
    
    
    
#----------------------------------------------------------


  
# CREATE DOG SIDE TESTING

	# TESTS TO MAKE SURE THE ADD DOG IS HANDLED CORRECLTY
  Scenario: 
    Given User is on Dog Profile Page
    And User has clicked SubmitNewDog button
    When User completes the NewDogForm
    And The User clicks submit
    Then The All Dogs Table should be visible
    And The New Dog is in the table
    And A confirmation message above All Dogs Table should be visible
    
    
    
