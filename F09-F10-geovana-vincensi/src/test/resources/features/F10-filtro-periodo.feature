# language: pt

Funcionalidade: F10 - Filtro por período de datas
    Como usuário do TockTock
    Quero filtrar minhas tarefas por um período
    Para focar no que eu tenho que fazer em certas datas

Cenário: Filtrar por data só mostra as tarefas no intervalo selecionado
    Dado que eu abri a aplicação
    E existe uma tarefa com o nome "Abrir o armário dos PCs" com a data "19/11/2026"
    E existe uma tarefa com o nome "Fechar o armário dos PCs" com a data "19/11/2026"
    E existe uma tarefa com o nome "Trabalho de IAGov :O" com a data "17/11/2026"
    E estou na tela do calendário
    Quando eu filtro as tarefas de "18/11/2026" até "21/11/2026"
    Então eu devo ver as tarefas "Abrir o armário dos PCs" e "Fechar o armário dos PCs"
    E não devo ver a tarefa "Trabalho de IAGov :O"

Cenário: Período sem tarefas
    Dado que eu abri a aplicação
    E existe uma tarefa com o nome "Abrir o armário dos PCs" com a data "19/11/2026"
    E existe uma tarefa com o nome "Fechar o armário dos PCs" com a data "19/11/2026"
    E existe uma tarefa com o nome "Trabalho de IAGov :O" com a data "17/11/2026"
    E estou na tela do calendário
    Quando eu filtro as tarefas de "01/01/2027" até "31/01/2027"
    Então devo ver a mensagem "Nenhuma tarefa nesse intervalo"
