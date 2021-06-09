@Regresion
Feature: LOGIN AND LOGOUT

  Scenario: I want to be able to authenticate myself against the API(JWT) using /auth/login endpoint
    Given I am an user in the auth/login endpoint
    When I login with the username test and the password 1234
    Then I am able to login

  Scenario: As a previously logged-in user, I want to be able to check my authentication credentials against /auth/me endpoint
    Given I am an user in the auth/login endpoint
    When I login with the username test and the password 1234
    And I check my authentication credentials
    Then I should be properly logged in

  Scenario: As a previously logged-in user, I want to be able to logout from the API using /auth/logout endpoint
    Given I am an user in the auth/login endpoint
    When I login with the username test and the password 1234
    And I logout
    Then I should be properly logged out