Feature: Issue Management E2E

    Scenario: Tester can create Issue with valid project id
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters information email "tester1@taskboard.com" and project Id "11111111-1111-1111-1111-111111111111" and title "Test Issue" and description "Test Description"
      And   the user clicks submit
      Then  then issue should be created

    Scenario: Tester cannot create Issue with invalid data
      Given the user is on the login page
      When  the user enters credentials "dev3@taskboard.com" and "devPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters information email "dev3@taskboard.com" and project Id "INVALID-PROJECT-ID" and title "Test Issue" and description "Test Description"
      And   the user clicks submit
      Then  then issue should not be created invalid data

    Scenario: Developer cannot create Issue with valid project id
      Given the user is on the login page
      When  the user enters credentials "dev3@taskboard.com" and "devPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters information email "dev3@taskboard.com" and project Id "11111111-1111-1111-1111-111111111111" and title "Test Issue" and description "Test Description"
      And   the user clicks submit
      Then  then issue should not be created invalid authorization

    Scenario: Tester can fetch an Issue by a valid Issue Id
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters information id "b1111111-1111-1111-1111-111111111111"
      And   the user clicks submit in fetch issue section
      Then  the issue should be fetched

    Scenario: Tester cannot fetch an Issue by a invalid Issue Id
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters information id "INVALID-ISSUE-ID"
      And   the user clicks submit in fetch issue section
      Then  the issue should not be fetched

    Scenario: Tester can filter issues by title
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user clicks the fetch issues button
      And   the user enters information to filter the title "Shopping"
      And   the user clicks the submit button in the filter issue section
      Then  the issues containing the filter title should appear

  Scenario: Tester can filter issues by selection option
    Given the user is on the login page
    When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
    And   the user attempts to login
    And   the user navigates to the issue page
    And   the user clicks the fetch issues button
    And   the user selects priority to filter the issues with "HIGH" priority
    And   the user clicks the submit button in the filter issue section
    Then  the issues containing the filter priority should appear

    Scenario: Tester can update an issues information
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters update information email "tester1@taskboard.com"  and issue id "b1111111-1111-1111-1111-111111111111" and title "Update Test Issue" and description "Update Test Description"
      And   the user clicks the update issue submit button
      Then  the issue should be updated

    Scenario: Tester cannot set an issue to in progress
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters update information email "tester1@taskboard.com"  and issue id "b1111111-1111-1111-1111-111111111111" and title "Update Test Issue" and description "Update Test Description" and status "In progress"
      And   the user clicks the update issue submit button
      Then  the issue should not be updated

    Scenario: Developer cannot set an issue to closed
      Given the user is on the login page
      When  the user enters credentials "dev3@taskboard.com" and "devPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters update information email "dev3@taskboard.com"  and issue id "b1111111-1111-1111-1111-111111111111" and title "Update Test Issue" and description "Update Test Description" and status "Closed"
      And   the user clicks the update issue submit button
      Then  the issue should not be updated

    Scenario: Tester can fetch all issues
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user clicks the fetch issues button
      Then  the issues should be fetched

    Scenario: Tester can delete an Issue by a valid Issue Id
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters information to delete id "b1111111-1111-1111-1111-111111111111"
      And   the user clicks submit in the delete issue section
      Then  the issue should be deleted

    Scenario: Tester cannot delete an Issue by a invalid Issue Id
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters information to delete id "INVALID-ISSUE-ID"
      And   the user clicks submit in the delete issue section
      Then  the issue should not be deleted


