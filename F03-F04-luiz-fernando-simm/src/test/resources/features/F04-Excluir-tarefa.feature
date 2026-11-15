#language pt

#language: pt

Funcionalidade: F04 - Excluir tarefa
  Como usuário do tocktock
  Quero Excluir minha tarefa
  Para Consultar os dados da tarefa

  Cenário:    Visualizar tarefa pendente
    Dado que o usuário esteja na tela inicial
    E tenha a tarefa "Regar Plantas" criada
    Quando eu clico na tarefa "Regar Plantas"
    E clico em excluir tarefa
    Então a tarefa "Regar Plantas" é excluída do sistema