# Preparacao dos quatro cenarios

Nao foi encontrado PDF da aula no workspace.

## Contrato de terminal proposto, ainda sem aplicacao para confirma-lo

O suporte reaproveita o exemplo de ra4-verival/steps/Terminal.java:
menus [tecla] Rotulo, tarefas NUMERO. NOME | DATA, prompt de fim de tela \n> .
Cada cenario inicia e encerra um processo real, com sessao inicialmente vazia.
Criacao usa Criar nova tarefa, Definir nome, Definir data e Salvar.

Para F09/F10, os steps propoem Calendario (com acento no codigo),
Definir dia, Definir mes (com acento), Definir data inicial,
Definir data final e Filtrar. A consulta de mes deve retornar todas as tarefas
com data daquele mes. Datas usam dd/MM/yyyy; mes usa MM/yyyy.
Os steps verificam apenas a saida real. Nao ha simulacao nem logica de dominio Java.
Esses rotulos sao uma proposta de interface, nao comportamento ja existente.

## Bloqueio observado

Python executa, mas python -u -m tocktock em ra4-verival retorna
No module named tocktock. Nao existe base para abrir o programa ou criar tarefas.
A verificacao inicial do suporte informa infraestrutura ausente antes de executar
as regras. Essa falha NAO comprova Red funcional de calendario/filtro.
Nenhuma base Python foi criada. Dependencias de criacao de tarefas e isolamento
precisam existir antes de obter a evidencia funcional.

O cenario de intervalo invertido foi preservado em F10-intervalo-invertido.md,
fora dos recursos executados. Nao versionar target/ nem o cache Maven.
