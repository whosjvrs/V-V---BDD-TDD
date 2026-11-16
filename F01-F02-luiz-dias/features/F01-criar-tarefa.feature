Feature: F01 - Criar tarefa
  As a TockTock user
  I want to create tasks
  So that I can organize what I need to do

  Background: Preparing the environment
    Given I have opened the app
    And I do not have any tasks

  Scenario: User with no tasks sees the option to create a task
    When I am on the main menu
    Then I must see the option "Criar nova tarefa"

  Scenario: Create a task with name and date
    When I select the "Criar nova tarefa" option
    And I fill in the task name with "Trabalho Verival"
    And I fill in the task date with "15/11/2026"
    And I select the "Salvar" option
    Then I must see the task "Trabalho Verival" in the list
    And the task "Trabalho Verival" must show the date "15/11/2026"
