Feature: User Profile

  # Navigate to Create New User
  Scenario: A user can navigate to create a new account
    Given a user is on the login page
    When the user clicks on the new account link
    Then the user will be on the new user profile page

  # User can create an account
  Scenario: A user can create a new account
    Given a use is on the new account page
    When the user enters valid account information
    And clicks on the submit button
    Then the user will be on the home page
    And and will have a new account

  # View User Profile page
  Scenario: A user can view their user information
    Given A user is logged in with "email1" "pass1" and on the homepage
    When User clicks on the <edit user profile> button
    Then User Profile page appears

  # Edit User information
  Scenario Outline: A user can edit their user information
    Given A user is logged in with "<username>" "<password>" and on the end user profile page
    When User enters new valid user information
    And User clicks the save button
    Then The user profile information is changed

    Examples: 
      | username | password |
      | owner    | secret   |
      | email1   | pass1    |
      | email2   | pass2    |
      | dogLover | pass6    |
