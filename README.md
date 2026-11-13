# TockTock

Gerenciador de tarefas (to-do list) com calendário, desenvolvido com **BDD** e **TDD** para a
**Avaliação Somativa RA4 – Parte 1** de Verificação e Validação (PUCPR – Engenharia de Software, 2026/2 – Prof. Marcelo Bardi).

**Stack:** Java 17+ · Maven · JUnit 5 (TDD) · Cucumber (BDD)

---

## Equipe e funcionalidades

| Integrante | Funcionalidade 1 | Funcionalidade 2 | Branch | Pasta |
|---|---|---|---|---|
| Luiz Dias | F01 – Criar tarefa | F02 – Editar tarefa | `luiz-dias` | `F01-F02-luiz-dias/` |
| Luiz Fernando Simm | F03 – Visualizar tarefas | F04 – Excluir tarefa | `luiz-fernando-simm` | `F03-F04-luiz-fernando-simm/` |
| Tabatha Martins | F05 – Status da tarefa (Concluída / Não concluída) | F06 – Status da tarefa (Farei / Não farei) | `tabatha-martins` | `F05-F06-tabatha-martins/` |
| João Victor dos Reis | F07 – Prioridade da tarefa | F08 – Tarefa recorrente | `joao-reis` | `F07-F08-joao-reis/` |
| Geovana Vincensi | F09 – Exibição de tarefa no calendário | F10 – Filtro por período de datas | `geovana-vincensi` | `F09-F10-geovana-vincensi/` |
| Rafael Sato | F11 – Criar lista de tarefas | F12 – Entrar em lista de tarefas | `rafael-sato` | `F11-F12-rafael-sato/` |

---

## Como o repositório funciona

- A `main` já vem com **uma pasta para cada integrante**. Cada pasta é um módulo Maven independente.
- Cada integrante trabalha **na sua branch** e **mexe só na sua pasta**.
- No fim, cada branch volta para a `main` por Pull Request. Como ninguém mexe na pasta do outro, **não há conflito** e tudo fica junto e organizado.

```
tocktock/
├── pom.xml                      ← pom pai (dependências comuns) — não alterar na sua branch
├── README.md
├── F01-F02-luiz-dias/
├── F03-F04-luiz-fernando-simm/
├── F05-F06-tabatha-martins/
├── F07-F08-joao-reis/           ← exemplo de uma pasta por dentro:
│   ├── pom.xml
│   ├── README.md
│   ├── evidencias/                          ← prints de cada passo do TDD
│   └── src/
│       ├── main/java/br/pucpr/tocktock/f0708/       ← código de produção
│       └── test/
│           ├── java/br/pucpr/tocktock/f0708/
│           │   ├── bdd/                             ← Step Definitions + RunCucumberTest
│           │   └── tdd/                             ← testes unitários JUnit 5
│           └── resources/features/                  ← cenários BDD (.feature)
├── F09-F10-geovana-vincensi/
└── F11-F12-rafael-sato/
```

O pacote de cada pasta segue o par de funcionalidades: `f0102`, `f0304`, `f0506`, `f0708`, `f0910`, `f1112`.

---

## Primeiros passos (cada integrante)

```bash
# 1. Clonar o repositório
git clone <URL-DO-REPOSITORIO>
cd tocktock

# 2. Criar a sua branch a partir da main (use o nome da tabela)
git checkout main
git pull
git checkout -b joao-reis

# 3. Publicar a branch
git push -u origin joao-reis
```

No **IntelliJ**: `File → Open` → selecione a pasta `tocktock` (a que tem o `pom.xml` pai) e abra como projeto Maven.
Instale o plugin **Cucumber for Java** (`Settings → Plugins`) para ter destaque de sintaxe e navegação nos `.feature`.

---

## Fluxo de trabalho

### 1. BDD — escreva os cenários primeiro

Crie um arquivo por funcionalidade em `src/test/resources/features/`, por exemplo `F07-prioridade.feature`.
Escreva em português, com `# language: pt` na **primeira linha**:

```gherkin
# language: pt
Funcionalidade: F07 - Prioridade da tarefa
  Como usuário do TockTock
  Quero definir a prioridade das minhas tarefas
  Para saber o que fazer primeiro

  Cenário: Tarefa criada sem prioridade recebe "Média"
    Dado que eu crio a tarefa "Estudar V&V" sem informar prioridade
    Quando eu consulto a tarefa
    Então a prioridade da tarefa deve ser "Média"
```

Depois implemente os passos (Step Definitions) no pacote `bdd`, usando as anotações em português
(`io.cucumber.java.pt.Dado`, `Quando`, `Então`). A classe `RunCucumberTest` já está pronta e roda todos os `.feature` da pasta.

### 2. TDD — Red → Green → Refactor

Os testes unitários ficam no pacote `tdd`. Para cada regra:

1. 🔴 **Red:** escreva um teste que falha e rode-o. **Tire print da falha.**
2. 🟢 **Green:** escreva o mínimo de código em `src/main/java` para o teste passar. **Tire print do teste passando.**
3. 🔵 **Refactor:** melhore o código mantendo os testes verdes. **Tire print.**

Salve os prints em `evidencias/` com o padrão `F07-01-red.png`, `F07-01-green.png`, `F07-01-refactor.png`, …
Eles vão direto para o **relatório dos passos do TDD**.

### 3. Commits

Faça um commit por passo do ciclo, assim o histórico também vira evidência:

| Tipo | Quando usar | Exemplo |
|---|---|---|
| `bdd` | cenários e step definitions | `bdd(F07): cenários de prioridade padrão e inválida` |
| `test` | 🔴 teste novo que falha | `test(F07): RED - prioridade padrão deve ser Média` |
| `feat` | 🟢 código que faz o teste passar | `feat(F07): GREEN - define Média como prioridade padrão` |
| `refactor` | 🔵 melhoria sem mudar comportamento | `refactor(F07): extrai enum Prioridade` |
| `docs` | README, evidências | `docs(F07): adiciona prints do ciclo 1` |

```bash
git add F07-F08-joao-reis/
git commit -m "test(F07): RED - prioridade padrão deve ser Média"
git push
```

### 4. Rodar os testes

```bash
mvn -pl F07-F08-joao-reis test     # só a sua pasta
mvn test                           # o projeto inteiro
```

O relatório HTML do Cucumber é gerado em `<sua-pasta>/target/cucumber-report.html`.
No IntelliJ também dá para rodar clicando no ▶ ao lado da classe de teste ou do cenário.

### 5. Juntar na main (Pull Request)

Quando a sua parte estiver pronta e com todos os testes passando:

```bash
git checkout main && git pull          # pega o que já foi integrado
git checkout joao-reis
git merge main                         # traz a main para a sua branch
mvn test                               # confere se tudo continua passando
git push
```

Abra um **Pull Request** da sua branch para a `main` no GitHub. Outro integrante revisa e faz o merge.

---

## Regras de convivência

- ✅ Mexa **apenas na sua pasta**.
- 🚫 **Não altere** o `pom.xml` da raiz nem o `README.md` principal na sua branch. Precisa de alguma mudança? Avise no grupo.
- 🚫 Nunca faça commit direto na `main`; tudo entra por Pull Request.
- 🚫 Não suba a pasta `target/` nem arquivos do IntelliJ (`.idea/`), o `.gitignore` já cuida disso.
- ✅ Só abra PR com `mvn test` passando.

---

## Entregáveis (15/11/2026, no AVA)

- Casos de teste BDD: arquivos `.feature` de todas as funcionalidades
- Relatório dos passos do TDD: descrição e print de cada passo (pasta `evidencias/` de cada integrante)
- Códigos gerados: código de produção e testes deste repositório
