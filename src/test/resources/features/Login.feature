@login
Feature: Login & Logout

  @login_1
  Scenario Outline: A user can login with their credentials
    Given the user is on the login page
    When the user enters their "<username>" and "<password>" and clicks the login button
    Then the user will be on the homepage

    Examples: 
      | username | password |
      | owner    | secret   |
      | email1   | pass1    |
      | email2   | pass2    |
      | dogLover | pass6    |

  @login_2
  Scenario: A user can logout
    Given the user is logged in as <email1> <pass1> and on the homepage
    When the user clicks the logout button in the nav bar
    Then the user will be logged out and on the login page
