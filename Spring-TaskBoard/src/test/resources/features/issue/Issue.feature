Feature: Issue Management E2E

    Scenario: Tester can create Issue with valid project id
      Given a user is logged in as a TESTER
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters information email "tester1@taskboard.com" and project Id "11111111-1111-1111-1111-111111111111" and title "Test Issue" and description "Test Description"
      And   the user clicks submit
      Then  then issue should be created

    Scenario: Tester can fetch an Issue by a valid Issue Id
      Given a user is logged in as a TESTER
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters information id "b1111111-1111-1111-1111-111111111111"
      And   the user clicks submit in fetch issue section
      Then  the issue should be fetched

    Scenario: Tester can update an issues information
      Given a user is logged in as a TESTER
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters update information email "tester1@taskboard.com"  and issue id "b1111111-1111-1111-1111-111111111111" and title "Update Test Issue" and description "Update Test Description"
      And   the user clicks the update issue submit button
      Then  the issue should be updated

    Scenario: Tester can fetch all issues
      Given a user is logged in as a TESTER
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user clicks the fetch issues button
      Then  the issues should be fetched

    Scenario: Tester can delete an Issue by a valid Issue Id
      Given a user is logged in as a TESTER
      Given the user is on the login page
      When  the user enters credentials "tester1@taskboard.com" and "testerPassword"
      And   the user attempts to login
      And   the user navigates to the issue page
      And   the user enters information to delete id "b1111111-1111-1111-1111-111111111111"
      And   the user clicks submit in the delete issue section
      Then  the issue should be deleted

