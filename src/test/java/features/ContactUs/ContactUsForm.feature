Feature: Contact Us Form

  Scenario: Submit Contact Us Form successfully
    Given I launch the browser
    When I navigate to url "http://automationexercise.com"
    Then I verify that home page is visible successfully
    When I click on "Contact Us" button
    Then I verify "GET IN TOUCH" is visible
    When I enter contact details with name "John Doe", email "johndoe@example.com", subject "Test Subject", and message "This is a test message"
    And I upload file "test-file.txt"
    And I click "Submit" button
    And I click OK on alert
    Then I verify success message "Success! Your details have been submitted successfully." is visible
    When I click "Home" button
    Then I verify that landed to home page successfully
