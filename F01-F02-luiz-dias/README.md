# F01-F02 – Luiz Dias

| Código | Funcionalidade |
|---|---|
| F01 | Criar tarefa |
| F02 | Editar tarefa |

**Branch:** `luiz-dias`

## Onde fica cada coisa

| O quê | Pasta |
|---|---|
| Cenários BDD (`.feature`) | `src/test/resources/features/` |
| Step Definitions do BDD | `src/test/java/br/pucpr/tocktock/f0102/bdd/` |
| Testes unitários (TDD) | `src/test/java/br/pucpr/tocktock/f0102/tdd/` |
| Código de produção | `src/main/java/br/pucpr/tocktock/f0102/` |
| Prints dos passos do TDD | `evidencias/` |

## Rodar só os meus testes

```bash
mvn -pl F01-F02-luiz-dias test
```
