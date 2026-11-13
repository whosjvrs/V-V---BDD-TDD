Feature: F01 - Criar tarefa
    as a TockTock user
    I want to create tasks
    to organize what I need to do

Background: Preparing the ambient
    Given I did opened the app
    And I do not have any task

Scenario: User with no tasks see a Create task Button   
    When I am in the home page
    Then I must see a button "Criar nova tarefa"

Scenario: Create a task with name and date
    When I click in the "Criar nova tarefa" button
    And Fills the tasks name with "Trabalho Verival"
    And Fills the tasks date with "15/11/2026"
    And Click in the "Salvar" button
    Then I must see the task "Trabalho Verival" in the list
    And The task "Trabalho Verival" must show the date "15/11/2026" 