#Author: tsherman786@revature.net
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
@tag
Feature: User Profile
  I want to use this template for my feature file

##Edit User information 
@tag1
	Scenario: A user can edit their user information
    Given A user is logged in
    And A user clicks on User Profile
    And A user has clicked Edit User Information
    When User clicks edit button
    And User edits data
    And User clicks Save button
    Then User is sent back to User Profile Page
    And Message box appears that changes were successful
    
@tag2
  Scenario: A user can edit their phone number
    Given A user is logged in
    And A user clicks on User Profile link
    And User can see the User Profile page
    And User clicks on Edit User Information button
    And User clicks on Edit button next to Phone Number
    When User enters a new Phone Number
    And Phone Number Input is valid
    And User clicks Save button
    Then User is sent back to User Profile Page
    And Message box appears that Phone Number was successfully changed
   
##View User Profile page   
@tag3 
  Scenario: User navigates to User Profile page
    Given A user is logged in
    And User is on home page
    When User clicks on User Profile
    Then User Profile page appears
    And <Edit User Information> button is displayed
    And <View User Information> is displayed
    And <Navigation Bar> is displayed
      
      
 ##View All User information
 @tag4
		Scenario: User can view all their user information
    Given A user is logged in
    And A user clicks on User Profile
    When A user clicks on View User Information
    Then All user information is displayed
      


    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |
