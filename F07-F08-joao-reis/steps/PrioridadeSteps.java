package f0708;

import static comum.Verifica.garantir;

import comum.Contexto;
import comum.Terminal;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import java.util.List;
import java.util.Map;

/** F07 - Prioridade da tarefa. */
public class PrioridadeSteps {
    private final Contexto contexto;

    public PrioridadeSteps(Contexto contexto) {
        this.contexto = contexto;
    }

    @Dado("que a tarefa {string} seja criada sem informar a prioridade")
    public void criadaSemPrioridade(String nome) {
        TarefaSteps.novaTarefa(contexto, nome);
    }

    @Dado("que a tarefa {string} seja criada com prioridade {string}")
    public void criadaComPrioridade(String nome, String prioridade) {
        TarefaSteps.novaTarefa(contexto, nome).preencher("prioridade", prioridade);
    }

    @Dado("que existem as tarefas:")
    public void existemTarefas(DataTable tabela) {
        for (Map<String, String> linha : tabela.asMaps()) {
            criar(linha.get("titulo"), linha.get("prioridade"));
        }
    }

    @Quando("eu clicar no botão de ordenar por prioridade")
    public void ordenar() {
        TarefaSteps.terminal(contexto).escolher("Ordenar por prioridade");
    }

    @Então("a tarefa {string} deve ter prioridade {string}")
    public void verPrioridade(String nome, String prioridade) {
        Terminal terminal = TarefaSteps.terminal(contexto);
        Terminal.LinhaTarefa linha = terminal.tarefas().get(nome);
        garantir(linha != null && linha.mostra("Prioridade: " + prioridade),
                "a tarefa \"" + nome + "\" não mostra a prioridade \"" + prioridade + "\":\n" + terminal.tela());
    }

    @Então("as tarefas devem aparecer na ordem:")
    public void verOrdem(DataTable tabela) {
        verLista(tabela.asList());
    }

    private void verLista(List<String> esperadas) {
        Terminal terminal = TarefaSteps.terminal(contexto);
        garantir(terminal.nomesDasTarefas().equals(esperadas),
                "esperava " + esperadas + " e a lista mostra " + terminal.nomesDasTarefas() + ":\n" + terminal.tela());
    }

    private void criar(String nome, String prioridade) {
        Terminal terminal = TarefaSteps.novaTarefa(contexto, nome);
        terminal.preencher("prioridade", prioridade);
        terminal.escolher("Salvar");
    }
}
