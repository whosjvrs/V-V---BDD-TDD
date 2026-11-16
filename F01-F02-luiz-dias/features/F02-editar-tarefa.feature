Feature: F02 - Editar tarefa
  As a TockTock user
  I want to edit my tasks
  So that I can keep them up to date

  Background: Preparing the environment
    Given I have opened the app
    And there is a task named "Trabalho Verival" with the date "15/11/2026"

  Scenario: Edit a task's name
    When I select the task "Trabalho Verival"
    And I select the "Editar" option
    And I change the task name to "Estudar BDD"
    And I select the "Salvar" option
    Then I must see the task "Estudar BDD" in the list
    And I must not see the task "Trabalho Verival" in the list
