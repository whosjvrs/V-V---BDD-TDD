# language: pt
Funcionalidade: F08 - Tarefa recorrente
  Como usuário do TockTock
  Quero que tarefas que se repetem sejam recriadas automaticamente
  Para não precisar cadastrá-las de novo a cada vez

  Regras:
  - As recorrências possíveis são "Diária", "Semanal" e "Mensal"
  - Uma tarefa recorrente precisa ter data de vencimento
  - Ao concluir uma ocorrência, o sistema cria a próxima como "não concluída",
  com o mesmo título e a mesma prioridade
  - Próximo vencimento: +1 dia (Diária), +7 dias (Semanal) ou mesmo dia do mês
  seguinte (Mensal); se o dia não existir no mês, usa o último dia do mês
  - Nenhuma ocorrência é criada com vencimento depois da data de término

  Cenário: Criar uma tarefa recorrente
    Dado que a tarefa "Academia" seja criada com vencimento em "10/11/2026" e recorrência "Semanal"
    Quando eu clicar no botão de criar tarefa
    Então a tarefa "Academia" deve ter recorrência "Semanal"

  Cenário: Rejeitar tarefa recorrente sem data de vencimento
    Dado que a tarefa "Academia" seja criada sem vencimento e com recorrência "Semanal"
    Quando eu clicar no botão de criar tarefa
    Então o sistema deve exibir o erro "Tarefa recorrente precisa de data de vencimento"
    E a tarefa "Academia" não deve ser criada

  Esquema do Cenário: Concluir uma ocorrência gera a próxima
    Dado que existe a tarefa "Tomar remédio" com vencimento em "<vencimento>" e recorrência "<recorrencia>"
    Quando eu clicar no botão de concluir da tarefa "Tomar remédio" com vencimento em "<vencimento>"
    Então deve existir uma ocorrência não concluída de "Tomar remédio" com vencimento em "<proximo>"

    Exemplos:
      | vencimento | recorrencia | proximo    |
      | 10/11/2026 | Diária      | 11/11/2026 |
      | 31/12/2026 | Diária      | 01/01/2027 |
      | 10/11/2026 | Semanal     | 17/11/2026 |
      | 10/11/2026 | Mensal      | 10/12/2026 |
      | 31/01/2027 | Mensal      | 28/02/2027 |
      | 31/01/2028 | Mensal      | 29/02/2028 |

  Cenário: A próxima ocorrência mantém o título e a prioridade
    Dado que existe a tarefa "Reunião de equipe" com vencimento em "13/11/2026", recorrência "Semanal" e prioridade "Alta"
    Quando eu clicar no botão de concluir da tarefa "Reunião de equipe" com vencimento em "13/11/2026"
    Então a ocorrência de "Reunião de equipe" com vencimento em "20/11/2026" deve ter prioridade "Alta"
