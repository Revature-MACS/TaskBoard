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
