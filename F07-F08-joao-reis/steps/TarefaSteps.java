package f0708;

import static comum.Verifica.garantir;

import comum.Contexto;
import comum.Terminal;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import java.io.IOException;

/** Passos comuns ao F07 e ao F08: abrir o app, criar, salvar e verificar erros. */
public class TarefaSteps {
    private final Contexto contexto;

    public TarefaSteps(Contexto contexto) {
        this.contexto = contexto;
    }

    /** Abre o app na primeira vez que um passo precisa dele. */
    static Terminal terminal(Contexto contexto) {
        if (contexto.terminal == null) {
            try {
                contexto.terminal = new Terminal();
            } catch (IOException erro) {
                throw new AssertionError("não consegui abrir o app: " + erro.getMessage(), erro);
            }
        }
        return contexto.terminal;
    }

    /** Abre o formulário de nova tarefa já com o nome preenchido. */
    static Terminal novaTarefa(Contexto contexto, String nome) {
        Terminal terminal = terminal(contexto);
        terminal.escolher("Criar nova tarefa");
        terminal.preencher("nome", nome);
        return terminal;
    }

    @Quando("eu clicar no botão de criar tarefa")
    public void clicarEmCriarTarefa() {
        terminal(contexto).escolher("Salvar");
    }

    @Então("o sistema deve exibir o erro {string}")
    public void verErro(String mensagem) {
        Terminal terminal = terminal(contexto);
        garantir(terminal.tela().contains("Erro: " + mensagem),
                "o erro \"" + mensagem + "\" não aparece na tela:\n" + terminal.tela());
    }

    @Então("a tarefa {string} não deve ser criada")
    public void tarefaNaoCriada(String nome) {
        Terminal terminal = terminal(contexto);
        terminal.escolher("Cancelar");
        garantir(!terminal.tarefas().containsKey(nome),
                "a tarefa \"" + nome + "\" foi criada mesmo assim:\n" + terminal.tela());
    }
}
