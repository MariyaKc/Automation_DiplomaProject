Feature: Test for Milestones

  Scenario: Milestone Test

    Given I open home page and click login
    When I verify, then login page was opened and i enter email and password
    And I click login button
    Then I create project
    When I go to Milestones Page
    Then I create 5 new milestone and verify, then milestones was created
    Then  I sort by "Title" descending and check
    And I sort by "Title" ascending and check
    Then I sort by "Due date" descending and check
    And I sort by "Due date" ascending and check
    Then I delete Milestones by count 5
    Then I delete Project