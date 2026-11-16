# TockTock

Gerenciador de tarefas **no terminal**, desenvolvido com **BDD** e **TDD** para a
**Avaliação Somativa RA4 – Parte 1** de Verificação e Validação (PUCPR – Engenharia de Software, 2026/2 – Prof. Marcelo Bardi).

**Stack:** app em **Python 3** (terminal) · cenários BDD em **Gherkin** · steps em **Java 17+** com **Cucumber** (sem Maven, via `cucumber.sh`).

Base do app e dos testes criada por Luiz Dias (repositório `ra4-verival`, histórico preservado aqui).

---

## Equipe e funcionalidades

| Integrante | Funcionalidade 1 | Funcionalidade 2 | Branch | Pasta |
|---|---|---|---|---|
| Luiz Dias | F01 – Criar tarefa | F02 – Editar tarefa | `luiz-dias` | `F01-F02-luiz-dias/` |
| Luiz Fernando Simm | F03 – Visualizar tarefas | F04 – Excluir tarefa | `luiz-fernando-simm` | `F03-F04-luiz-fernando-simm/` |
| Tabatha Martins | F05 – Status (Concluída / Não concluída) | F06 – Status (Farei / Não farei) | `tabatha-martins` | `F05-F06-tabatha-martins/` |
| João Victor dos Reis | F07 – Prioridade da tarefa | F08 – Tarefa recorrente | `joao-reis` | `F07-F08-joao-reis/` |
| Geovana Vincensi | F09 – Exibição no calendário | F10 – Filtro por período de datas | `geovana-vincensi` | `F09-F10-geovana-vincensi/` |
| Rafael Sato | F11 – Criar lista de tarefas | F12 – Entrar em lista de tarefas | `rafael-sato` | `F11-F12-rafael-sato/` |

---

## Estrutura

```
V-V---BDD-TDD/
├── cucumber.sh              ← roda os cenários (todos ou só os de uma pasta)
├── tocktock/                ← NÚCLEO do app (ninguém edita na sua branch)
│   ├── app.py, telas.py, tarefa.py, gerenciador.py
│   ├── extensoes.py         ← pontos de extensão que cada funcionalidade usa
│   └── funcionalidades.py   ← carrega automaticamente Fxx-Fyy-*/app/*.py
├── comum/steps/             ← infraestrutura dos testes (Terminal, Contexto, steps genéricos)
├── F01-F02-luiz-dias/
│   ├── features/            ← cenários .feature
│   ├── steps/               ← step definitions em Java (pacote f0102)
│   ├── app/                 ← código Python das SUAS funcionalidades
│   └── evidencias/          ← prints de cada passo
├── F03-F04-luiz-fernando-simm/   (mesma estrutura)
└── ...
```

**Regra de ouro:** cada integrante mexe **apenas na sua pasta**. O núcleo (`tocktock/`), `comum/` e
`cucumber.sh` são compartilhados. Precisa mudar algo neles? Avise no grupo.

---

## Como rodar

**Requisitos:** Python 3.10+, Java 17+, `curl` e Bash (Linux, macOS ou Git Bash no Windows).

```bash
python3 -m tocktock                      # abre o app no terminal

./cucumber.sh                            # todos os cenários de todas as pastas
./cucumber.sh F07-F08-joao-reis          # só os cenários de uma pasta
./cucumber.sh F07-F08-joao-reis/features/F07-prioridade.feature   # um arquivo
```

Na primeira execução o script baixa as bibliotecas do Cucumber para `build/lib/`.
O relatório HTML fica em `build/relatorio-cucumber.html` (bom para os prints).

---

## Como criar a sua funcionalidade

### 1. Cenários (`features/`)

Arquivos `.feature` em português (`# language: pt` na primeira linha) ou em inglês, sempre com **Dado / Quando / Então**.

### 2. Step definitions (`steps/`)

Classes Java no pacote da sua pasta (`f0304`, `f0506`, `f0708`, `f0910`, `f1112`). Use o `Terminal` para
operar o app como um usuário:

```java
package f0708;

import static comum.Verifica.garantir;

import comum.Contexto;
import comum.Terminal;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;

public class PrioridadeSteps {
    private final Contexto contexto;

    public PrioridadeSteps(Contexto contexto) {   // o Contexto é compartilhado entre todas as classes de steps
        this.contexto = contexto;
    }

    @Dado("que eu abri o TockTock")
    public void abrir() throws java.io.IOException {
        contexto.terminal = new Terminal();
    }
    // ...
}
```

API do `Terminal` (em `comum/steps/Terminal.java`):

| Método | O que faz |
|---|---|
| `escolher("Salvar")` | escolhe a opção pelo rótulo (`[S] Salvar`) |
| `preencher("nome", "Estudar")` | escolhe "Definir nome" e digita o valor (vale para campos extras: `preencher("prioridade", "Alta")`) |
| `abrirTarefa("Estudar")` | abre a tarefa pelo nome na lista |
| `tarefas()` | mapa nome → linha (`numero()`, `data()`, `colunas()`, `mostra("Alta")`) |
| `nomesDasTarefas()` | nomes na ordem exibida |
| `tela()`, `titulo()`, `opcoes()` | texto da tela atual |

Steps genéricos já prontos em inglês (`comum/steps/AppSteps.java`): `I have opened the app`,
`I select the {string} option`, `I must see the task {string} in the list`, etc.
**Não redefina** um passo que já existe (o Cucumber acusa passo duplicado).

### 3. Código do app (`app/`)

Crie um arquivo `.py` na pasta `app/` da sua pasta. Ele é carregado sozinho, sem editar o núcleo.
Registre o que a sua funcionalidade precisa em `tocktock.extensoes`:

| Decorador / função | Para quê |
|---|---|
| `@ext.opcao_inicio` | nova opção na tela inicial: `return {"P": ("Filtrar por prioridade", acao)}` |
| `@ext.opcao_tarefa` | nova opção na tela de uma tarefa (ex.: Concluir, Excluir) |
| `ext.campo(chave, rotulo, pergunta, padrao)` | novo campo no formulário de tarefa (vira "Definir ...") |
| `@ext.validacao` | `(valores, tarefa)` → `raise ValueError("mensagem")` recusa o salvamento e mostra `Erro: mensagem` |
| `@ext.coluna` | `(tarefa)` → texto extra na linha da lista (`1. Nome \| data \| Alta`) |
| `@ext.visao` | `(tarefas, app)` → ordena ou filtra a lista da tela inicial |
| `@ext.linhas_inicio` | `(app)` → linhas extras no topo da tela inicial (ex.: `Filtro: Alta`) |
| `@ext.apos_salvar` | `(tarefa, app)` → executa algo depois de salvar |

Os dados extras de cada tarefa ficam em `tarefa.campos` (ex.: `tarefa.campos["prioridade"]`).
O estado das telas (filtros, ordenação) fica em `app.estado`, usando chaves com o código da sua funcionalidade.
Veja o exemplo completo em `F01-F02-luiz-dias/app/f0102_criar_editar.py`.

### Convenções para não haver conflito

| Item | Convenção |
|---|---|
| Teclas usadas | Início: `N` (F01). Tarefa: `E` (F02). Combine no grupo antes de usar uma tecla nova; o app acusa tecla repetida. |
| Status de conclusão (F05) | `tarefa.campos["concluida"]` (`True`/`False`). F07 e F08 leem esse campo. |
| Datas | Texto no formato `dd/mm/aaaa`, como no F01. |

---

## Fluxo BDD + TDD

1. **Cenários** em `features/` → commit `bdd(F07): cenários ...`
2. **Steps** em `steps/` → rode `./cucumber.sh <sua-pasta>`: os cenários falham. **🔴 RED** → print + commit.
3. **Código mínimo** em `app/` para passar → **🟢 GREEN** → print + commit.
4. **Refatoração** mantendo tudo verde → **🔵 REFACTOR** → print + commit.
5. Repita por cenário/regra. Salve os prints em `evidencias/` (`F07-C01-red.png`, `F07-C01-green.png`, ...).

---

## Git

```bash
git checkout main && git pull
git checkout <sua-branch>
git merge main                    # traz o núcleo e a infraestrutura atualizados
git add <sua-pasta>/              # nunca use "git add ." nem "git commit -a"
git status                        # só arquivos da sua pasta
git commit -m "..."
git push
```

Ao final, abra um Pull Request da sua branch para a `main`.
