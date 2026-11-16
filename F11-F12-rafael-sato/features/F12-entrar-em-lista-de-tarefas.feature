Feature: Enter task list
    As a TockTock user
    I want to enter a task list
    So that I can see only the tasks that belong to it

    Background: Preparing scenario
        Given I am logged in
        And I am on the main menu

    Scenario: Enter a list that has tasks
        Given there is one list named "Faculdade"
        And the list "Faculdade" has the task "Estudar Verival"
        When I enter the list "Faculdade"
        Then I must see the task "Estudar Verival"

    Scenario: Enter an empty list
        Given there is one list named "Mercado"
        When I enter the list "Mercado"
        Then I must see the message "This list has no tasks."

    Scenario: Enter a list shows only its own tasks
        Given there is one list named "Faculdade"
        And there is one list named "Casa"
        And the list "Faculdade" has the task "Estudar Verival"
        And the list "Casa" has the task "Lavar louça"
        When I enter the list "Faculdade"
        Then I must see the task "Estudar Verival"
        And I must not see the task "Lavar louça"
