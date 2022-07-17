Feature: User Profile

  ## View User Profile page
  Scenario: User navigates to User Profile page
    Given A user is logged in
    And User is on home page
    When User clicks on User Profile
    Then User Profile page appears
    And <Edit User Information> button is displayed
    And <View User Information> is displayed
    And <Navigation Bar> is displayed

  ## Edit User information
  Scenario Outline: A user can edit their user information
    Given A user is logged in with "<username>" "<password>" and on the homepage
    And The user is on the edit-userProfile section
    When User clicks on the <edit userpage> button
    And User edits data
    And User clicks Save button
    Then User is sent back to User Profile Page
    And Message box appears that changes were successful

    Examples: 
      | username | password |
      | owner    | secret   |
      | email1   | pass1    |
      | email2   | pass2    |
      | dogLover | pass6    |