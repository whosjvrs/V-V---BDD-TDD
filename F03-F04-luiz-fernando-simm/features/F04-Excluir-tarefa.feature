# language: pt

Funcionalidade: F04 - Excluir tarefa
  Como usuário do TockTock
  Quero excluir uma tarefa
  Para manter minha lista de tarefas organizada

  Cenário: Excluir tarefa pendente
    Dado que o usuário esteja na tela inicial
    E tenha a tarefa "Regar Plantas" criada
    Quando o usuário clicar na tarefa "Regar Plantas"
    E clicar na opção "Excluir tarefa"
    Então a tarefa "Regar Plantas" não deverá mais aparecer na lista de tarefas

