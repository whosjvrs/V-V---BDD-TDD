package f0708;

import static comum.Verifica.garantir;

import comum.Contexto;
import comum.Terminal;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;

/** F08 - Tarefa recorrente. */
public class RecorrenciaSteps {
    private final Contexto contexto;

    public RecorrenciaSteps(Contexto contexto) {
        this.contexto = contexto;
    }

    @Dado("que a tarefa {string} seja criada com vencimento em {string} e recorrência {string}")
    public void criadaComRecorrencia(String nome, String vencimento, String recorrencia) {
        Terminal terminal = TarefaSteps.novaTarefa(contexto, nome);
        terminal.preencher("data", vencimento);
        terminal.preencher("recorrência", recorrencia);
    }

    @Dado("que a tarefa {string} seja criada sem vencimento e com recorrência {string}")
    public void criadaSemVencimento(String nome, String recorrencia) {
        TarefaSteps.novaTarefa(contexto, nome).preencher("recorrência", recorrencia);
    }

    @Então("a tarefa {string} deve mostrar a próxima ocorrência em {string}")
    public void verProximaOcorrencia(String nome, String data) {
        Terminal terminal = TarefaSteps.terminal(contexto);
        Terminal.LinhaTarefa linha = terminal.tarefas().get(nome);
        garantir(linha != null && linha.mostra("Próxima: " + data),
                "a tarefa \"" + nome + "\" não mostra a próxima ocorrência em " + data + ":\n" + terminal.tela());
    }
}
