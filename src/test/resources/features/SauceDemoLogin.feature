Feature: Sauce Demo login Test

  @login @regression
  Scenario: login to sauceDemo and verify user logged in
    Given  user is in sauce Demo login page
    When user proved valid username
    And user provides valid password
    And user clicks on login button
    Then verify user logged in
