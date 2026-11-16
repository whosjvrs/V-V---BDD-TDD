# language: pt

Funcionalidade: F06 - Status da tarefa (Farei / Não farei)
    Como usuário do TockTock
    Quero indicar se vou ou não fazer uma tarefa
    Para separar o que ainda está nos meus planos do que eu decidi não fazer

  Cenário: Tarefa nova começa como farei
    Dado que eu criei a tarefa "Ler o pdf de Qualidade de Software" com a data "18/11/2026"
    Quando eu consulto a tarefa "Ler o pdf de Qualidade de Software"
    Então o status de execução da tarefa deve ser "Farei"

  Cenário: Marcar uma tarefa como não farei
    Dado que existe a tarefa "Ler o pdf de Qualidade de Software" com status "Farei"
    Quando eu marco a tarefa "Ler o pdf de Qualidade de Software" como "Não farei"
    Então o status de execução da tarefa deve ser "Não farei"

  Cenário: Voltar uma tarefa para farei
    Dado que existe a tarefa "Ler o pdf de Qualidade de Software" com status "Não farei"
    Quando eu marco a tarefa "Ler o pdf de Qualidade de Software" como "Farei"
    Então o status de execução da tarefa deve ser "Farei"
