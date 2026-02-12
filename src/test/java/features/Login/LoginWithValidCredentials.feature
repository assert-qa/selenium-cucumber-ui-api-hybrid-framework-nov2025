Feature: Login With Valid Credentials

  Scenario: User login with valid email and password
    Given I launch the browser
    When I navigate to url "http://automationexercise.com"
    Then I verify that home page is visible successfully
    When I click on "Signup / Login" button
    Then I verify "Login to your account" is visible
    When I enter correct email address and password
    And I click "login" button
    Then I verify that "Logged in as username" is visible
    When I click "Delete Account" button
    Then I verify that "ACCOUNT DELETED!" is visible

