# language: pt
Funcionalidade: F07 - Prioridade da tarefa
  Como usuário do TockTock
  Quero definir a prioridade das minhas tarefas
  Para saber o que devo fazer primeiro

  Regras:
  - As prioridades possíveis são "Baixa", "Média" e "Alta"
  - Tarefa criada sem prioridade recebe "Média"
  - Qualquer outro valor de prioridade é rejeitado
  - Não é possível alterar a prioridade de uma tarefa concluída
  - As tarefas podem ser ordenadas e filtradas por prioridade

  Cenário: Tarefa criada sem prioridade recebe prioridade "Média"
    Dado que a tarefa "Estudar V&V" seja criada sem informar a prioridade
    Quando eu clicar no botão de criar tarefa
    Então a tarefa "Estudar V&V" deve ter prioridade "Média"

  Esquema do Cenário: Criar tarefa com prioridade válida
    Dado que a tarefa "<titulo>" seja criada com prioridade "<prioridade>"
    Quando eu clicar no botão de criar tarefa
    Então a tarefa "<titulo>" deve ter prioridade "<prioridade>"

    Exemplos:
      | titulo       | prioridade |
      | Lavar louça  | Baixa      |
      | Pagar boleto | Média      |
      | Entregar RA4 | Alta       |

  Cenário: Alterar a prioridade de uma tarefa
    Dado que existe a tarefa "Pagar boleto" com prioridade "Baixa"
    E que a prioridade da tarefa "Pagar boleto" seja alterada para "Alta"
    Quando eu clicar no botão de salvar
    Então a tarefa "Pagar boleto" deve ter prioridade "Alta"

  Cenário: Não permitir alterar a prioridade de uma tarefa concluída
    Dado que existe a tarefa "Pagar boleto" com prioridade "Baixa"
    E que a tarefa "Pagar boleto" esteja concluída
    E que a prioridade da tarefa "Pagar boleto" seja alterada para "Alta"
    Quando eu clicar no botão de salvar
    Então o sistema deve exibir o erro "Não é possível alterar a prioridade de uma tarefa concluída"
    E a tarefa "Pagar boleto" deve ter prioridade "Baixa"

