# language: pt
Funcionalidade: F08 - Tarefa recorrente
  Como usuário do TockTock
  Quero marcar tarefas que se repetem
  Para saber quando será a próxima ocorrência sem precisar calcular

  Regras:
  - As recorrências possíveis são "Nenhuma", "Diária", "Semanal" e "Mensal" (padrão: "Nenhuma")
  - Uma tarefa recorrente precisa ter data de vencimento
  - A lista mostra a próxima ocorrência: +1 dia (Diária), +7 dias (Semanal) ou mesmo dia
    do mês seguinte (Mensal); se o dia não existir no mês, usa o último dia do mês

  Esquema do Cenário: Calcular a próxima ocorrência
    Dado que a tarefa "Tomar remédio" seja criada com vencimento em "<vencimento>" e recorrência "<recorrencia>"
    Quando eu clicar no botão de criar tarefa
    Então a tarefa "Tomar remédio" deve mostrar a próxima ocorrência em "<proxima>"

    Exemplos:
      | vencimento | recorrencia | proxima    |
      | 10/11/2026 | Diária      | 11/11/2026 |
      | 31/12/2026 | Diária      | 01/01/2027 |
      | 10/11/2026 | Semanal     | 17/11/2026 |
      | 10/11/2026 | Mensal      | 10/12/2026 |
      | 31/01/2027 | Mensal      | 28/02/2027 |
      | 31/01/2028 | Mensal      | 29/02/2028 |

  Cenário: Rejeitar tarefa recorrente sem data de vencimento
    Dado que a tarefa "Academia" seja criada sem vencimento e com recorrência "Semanal"
    Quando eu clicar no botão de criar tarefa
    Então o sistema deve exibir o erro "Tarefa recorrente precisa de data de vencimento."
    E a tarefa "Academia" não deve ser criada

  Cenário: Rejeitar recorrência inválida
    Dado que a tarefa "Academia" seja criada com vencimento em "10/11/2026" e recorrência "Anual"
    Quando eu clicar no botão de criar tarefa
    Então o sistema deve exibir o erro "Recorrência inválida. Use Nenhuma, Diária, Semanal ou Mensal."
    E a tarefa "Academia" não deve ser criada
