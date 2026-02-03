@smokeTest
Feature: Login with Invalid Credentials

  Scenario: User login with incorrect email and password
    Given I launch the browser
    When I navigate to url "http://automationexercise.com"
    Then I verify that home page is visible successfully
    When I click on "Signup / Login" button
    Then I verify "Login to your account" is visible
    When I enter incorrect email address "wrongemail@test.com" and password "wrongpassword"
    Then I verify error "Your email or password is incorrect!" is visible
