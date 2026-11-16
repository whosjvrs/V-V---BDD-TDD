# Cenário preservado fora da execução

Original mantido como referência; não faz parte dos quatro cenários desta entrega.

```gherkin
Cenario: A data inicial do intervalo deve ser anterior que a final
    Quando eu filtro as tarefas de "31/01/2027" até "01/01/2027"
    Entao devo ver o alerta "A data inicial do intervalo deve ser anterios que a final"
```
