Feature: Create task list
    As a TockTock user
    I want to create a Task list
    So that I can organize my tasks

    Background: Preparing scenario
        Given I am logged in
        And I am on the main menu

    Scenario: Create task list with a unique name
        Given There is no list created
        When I create a list with the name "Lista 1"
        Then I must see the list "Lista 1" on the sidebar

    Scenario: Create task list with a duplicated name
        Given there is one list named "Lista 2"
        When I create a list with the name "Lista 2"
        Then I must see the error "A list with this name already exists."
        And there must not be any new list created

    Scenario: Create task list with an empty name
        Given There is no list created
        When I create a list with the name ""
        Then I must see the error "The list name is required."
        And there must not be any new list created

    Scenario: Create task list with a name containing only spaces
        Given There is no list created
        When I create a list with the name "   "
        Then I must see the error "The list name is required."
        And there must not be any new list created