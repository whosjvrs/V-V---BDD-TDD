# language: pt

Funcionalidade: F05 - Status da tarefa (Concluída / Não concluída)
    Como usuário do TockTock
    Quero marcar minhas tarefas como concluídas ou não concluídas
    Para acompanhar o que eu já terminei e o que ainda falta fazer

  Cenário: Tarefa nova começa como não concluída
    Dado que eu criei a tarefa "Estudar Verival" com a data "15/11/2026"
    Quando eu consulto a tarefa "Estudar Verival"
    Então o status de conclusão da tarefa deve ser "Não concluída" e sem data de conclusão

  Cenário: Marcar uma tarefa como concluída
    Dado que existe a tarefa "Estudar Verival" com status "Não concluída"
    Quando eu marco a tarefa "Estudar Verival" como "Concluída"
    Então o status de conclusão da tarefa deve ser "Concluída" com a data de conclusão registrada

  Cenário: Desmarcar uma tarefa concluída
    Dado que existe a tarefa "Estudar Verival" com status "Concluída"
    Quando eu marco a tarefa "Estudar Verival" como "Não concluída"
    Então o status de conclusão da tarefa deve ser "Não concluída" e sem data de conclusão

  Cenário: Tarefa marcada como "Não farei" não pode ser concluída
    Dado que existe a tarefa "Estudar Verival" com status "Não farei"
    Quando eu marco a tarefa "Estudar Verival" como "Concluída"
    Então devo ver o alerta "Não é possível concluir uma tarefa marcada como Não farei"
