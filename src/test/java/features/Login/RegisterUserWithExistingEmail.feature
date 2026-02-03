@smokeTest
Feature: Register User With Existing Email

  Scenario: User tries to register with existing email
    Given I launch the browser
    When I navigate to url "http://automationexercise.com"
    Then I verify that home page is visible successfully
    When I click on "Signup / Login" button
    Then I verify "New User Signup!" is visible
    When I enter name "Test User" and already registered email address "mahendra_test@mail.com"
    And I click "Signup" button
    Then I verify error message "Email Address already exist!" is visible
