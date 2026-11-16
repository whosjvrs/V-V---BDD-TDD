#language: pt

Funcionalidade: F03 - Visualizar Tarefa
    Como usuário do tocktock
    Quero Visualizar minhas tarefas
    Para Consultar os dados da tarefa

    Cenário:    Visualizar tarefa pendente
        Dado que o usuário esteja na tela inicial
        E tenha a tarefa "Regar Plantas" criada
        Quando eu clico na tela "Regar Plantas"
        Então a tarefa "Regar Plantas" tem seus dados exibidos na tela
