# language: pt

Funcionalidade: F09 - Exibição de tarefa no calendário
    Como usuário do TockTock
    Quero ver minhas tarefas no calendário
    Para saber o que tenho em cada dia

Cenário: Tarefa aparece no dia da sua data
    Dado que eu abri a aplicação
    E existe uma tarefa com o nome "Estudar Verival" com a data "14/11/2026"
    E existe uma tarefa com o nome "Ler o pdf de Qualidade de Software" sem nenhuma data
    E estou na tela do calendário
    Quando eu consulto o dia "14/11/2026" do calendário
    Então devo ver a tarefa "Estudar Verival"

Cenário: Tarefa sem data não aparece no calendário
    Dado que eu abri a aplicação
    E existe uma tarefa com o nome "Estudar Verival" com a data "14/11/2026"
    E existe uma tarefa com o nome "Ler o pdf de Qualidade de Software" sem nenhuma data
    E estou na tela do calendário
    Quando eu vejo o calendário do mês "11/2026"
    Então não devo ver a tarefa "Ler o pdf de Qualidade de Software" em nenhum dia
