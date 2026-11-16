package f0506;

import static comum.Verifica.garantir;

import comum.Contexto;
import comum.Terminal;
import io.cucumber.java.pt.Então;

/** F06 - Status da tarefa (Farei / Não farei). */
public class ExecucaoSteps {
    private final Contexto contexto;
    private final TarefaAtual atual;

    public ExecucaoSteps(Contexto contexto, TarefaAtual atual) {
        this.contexto = contexto;
        this.atual = atual;
    }

    @Então("o status de execução da tarefa deve ser {string}")
    public void statusDeExecucao(String status) {
        Terminal.LinhaTarefa linha = contexto.terminal.tarefas().get(atual.nome);
        garantir(linha != null && linha.mostra(status),
                "a tarefa \"" + atual.nome + "\" não mostra o status \"" + status + "\":\n"
                        + contexto.terminal.tela());
    }
}
