@Regresion
Feature: AGENDA CONTACTS

  Background: El usuario ha iniciado correctamente la creacion de un nuevo proceso
    Given I am an user in the auth/login endpoint
    When I login with the username test and the password 1234

  Scenario Outline: I want to be allowed to store new contact data, so that I can create new contacts in the Contacts API and get an unique id
    When I store the firstName <firstName>, the lastName <lastName>, the email <email>, the phone <phone> and the mobile <mobile> as a new contact
    Then The new contact is stored with an unique id
    Examples:
    | firstName  | lastName  | email                | phone     | mobile    |
    | Mario      | Gomez     | mario.gomez@test.com | 542321    | 98765     |
    | Erika      | Perez     |                      |           |           |

  Scenario: I want to not allow two entries with the same firstNameand lastName, so that I cannot have duplicates in my agenda
    When I store the firstName Juan, the lastName Morales, the email email@test.com, the phone 43210 and the mobile 56789 as a new contact
    And I try to store a new contact with the same data
    Then The new contact is not stored

  Scenario: I want to retrieve all previously created contacts, so I can retrieve my full agenda
    When I retrieve all the previously created contacts
    Then I can verify that the contacts information is correct

  Scenario: I want to retrieve previously created contact by unique id, so I can retrieve a specific agenda entry
    When I store the firstName Paula, the lastName Milles, the email test@email.com, the phone 43210 and the mobile 56789 as a new contact
    And I retrieve the id of the previously created contact
    Then I can verify that the contact information is correct for the specific id

  Scenario Outline: I want to update any field of a previously created contact by unique id, so I can keep my agenda up to date
    When I store the firstName Paula, the lastName Milles, the email test@email.com, the phone 43210 and the mobile 56789 as a new contact
    And I modify the field <field> with the data <data>
    Then the agenda is up to date with the changes
    Examples:
      | field         | data             |
      | firstName     | Monica           |
      | lastName      | Lopez            |
      | phone         | 12345            |
      | mobile        | 98765            |
      | email         | test1@email.com  |