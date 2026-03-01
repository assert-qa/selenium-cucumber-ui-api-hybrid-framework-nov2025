Feature: Verify Test Cases Page

  Scenario: Verify user is navigated to test cases page successfully
    Given I launch the browser
    When I navigate to url "http://automationexercise.com"
    Then I verify that home page is visible successfully
    When I click on "Test Cases" button
    Then I verify user is navigated to test cases page successfully
