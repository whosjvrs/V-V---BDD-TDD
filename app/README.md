# app – Interface JavaFX do TockTock

Janela principal do sistema: uma aba **Início** e **uma aba por integrante**.
Enquanto a funcionalidade não está pronta, a aba mostra o painel "EM CONSTRUÇÃO".

## Como rodar

**IntelliJ:** abra `app/src/main/java/br/pucpr/tocktock/app/Launcher.java` e clique no ▶ ao lado do `main`.

**Terminal (na raiz do repositório):**

```bash
mvn install -DskipTests      # compila todos os módulos (só na primeira vez ou quando a lógica mudar)
mvn -pl app javafx:run       # abre a janela
```

## Como ligar a sua funcionalidade à tela

> A tela é o **último passo**. Primeiro: cenários BDD → ciclos TDD (JUnit) → cenários BDD verdes.
> A interface não é testada: ela só chama a lógica que os testes já validaram.

1. Altere **somente a sua classe** em `app/src/main/java/br/pucpr/tocktock/app/abas/`:

   | Integrante | Arquivo |
   |---|---|
   | Luiz Dias | `AbaF01F02.java` |
   | Luiz Fernando Simm | `AbaF03F04.java` |
   | Tabatha Martins | `AbaF05F06.java` |
   | João Victor dos Reis | `AbaF07F08.java` |
   | Geovana Vincensi | `AbaF09F10.java` |
   | Rafael Sato | `AbaF11F12.java` |

2. No método `criarConteudo()`, troque `PainelEmConstrucao.para(this)` pela sua tela
   (campos, botões, lista), chamando as classes do seu módulo (ex.: `br.pucpr.tocktock.f0708`).
   O `app/pom.xml` já depende de todos os módulos: **não precisa alterá-lo**.

3. Os botões da tela devem chamar os **mesmos métodos** que os seus Step Definitions chamam
   no "Quando eu clicar no botão de ...". Assim, o que o Cucumber testou é exatamente o que a tela executa.

Exemplo de esqueleto:

```java
@Override
public Node criarConteudo() {
    TextField titulo = new TextField();
    Button criar = new Button("Criar tarefa");
    Label mensagem = new Label();

    criar.setOnAction(e -> {
        try {
            servico.criarTarefa(titulo.getText());   // mesma chamada dos Step Definitions
            mensagem.setText("Tarefa criada!");
        } catch (IllegalArgumentException erro) {
            mensagem.setText(erro.getMessage());
        }
    });

    return new VBox(10, titulo, criar, mensagem);
}
```

## Não altere

`TockTockApp.java`, `Aba.java`, `PainelEmConstrucao.java`, `Launcher.java`, `styles.css` e `app/pom.xml`
são compartilhados. Precisa de alguma mudança neles? Avise no grupo.
