Feature: F02 - Editar tarefa
    as a TockTock user
    I want to create tasks
    to organize what I need to do

Background: Preparing the ambient
    Given I did opened the app
    And There is a task named "Trabalho Verival" with the date "15/11/2026"

Scenario: Edit a tasks name   
    When I select a the task "Trabalho Verival"
    And Click in the "Editar" button
    And Change the tasks name to "Estudar BDD"
    And Click in the "Salvar" button
    Then I must see the "Estudar BDD" task 
    And I must not see the "Trabalho Verival" task in the list 
