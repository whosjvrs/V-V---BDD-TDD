package f0506;

import static comum.Verifica.garantir;

import comum.Contexto;
import comum.Terminal;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import java.io.IOException;
import java.util.Locale;

/** Passos "Dado" e "Quando" usados pelos cenários de F05 e de F06. */
public class StatusSteps {
    private static final String DATA_PADRAO = "15/11/2026";
    private static final Locale PORTUGUES = Locale.forLanguageTag("pt-BR");

    private final Contexto contexto;
    private final TarefaAtual atual;

    public StatusSteps(Contexto contexto, TarefaAtual atual) {
        this.contexto = contexto;
        this.atual = atual;
    }

    @Dado("que eu criei a tarefa {string} com a data {string}")
    public void criarTarefa(String nome, String data) throws IOException {
        contexto.terminal = new Terminal();
        Terminal terminal = contexto.terminal;
        terminal.escolher("Criar nova tarefa");
        terminal.preencher("nome", nome);
        terminal.preencher("data", data);
        terminal.escolher("Salvar");
        atual.nome = nome;
    }

    @Dado("que existe a tarefa {string} com status {string}")
    public void tarefaComStatus(String nome, String status) throws IOException {
        criarTarefa(nome, DATA_PADRAO);
        // "Não concluída" e "Farei" são os status padrão de uma tarefa nova.
        if (status.equals("Concluída") || status.equals("Não farei")) {
            marcar(nome, status);
        }
    }

    @Quando("eu consulto a tarefa {string}")
    public void consultar(String nome) {
        atual.nome = nome;
        garantir(contexto.terminal.tarefas().containsKey(nome),
                "a tarefa \"" + nome + "\" não aparece na lista:\n" + contexto.terminal.tela());
    }

    @Quando("eu marco a tarefa {string} como {string}")
    public void marcar(String nome, String status) {
        atual.nome = nome;
        contexto.terminal.abrirTarefa(nome);
        contexto.terminal.escolher("Marcar como " + status.toLowerCase(PORTUGUES));
    }
}
