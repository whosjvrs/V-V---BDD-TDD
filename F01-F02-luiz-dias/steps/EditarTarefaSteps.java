package f0102;

import comum.Contexto;
import comum.Terminal;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class EditarTarefaSteps {
    private final Contexto contexto;

    public EditarTarefaSteps(Contexto contexto) {
        this.contexto = contexto;
    }

    @Given("there is a task named {string} with the date {string}")
    public void tarefaExistente(String nome, String data) {
        Terminal terminal = contexto.terminal;
        terminal.escolher("Criar nova tarefa");
        terminal.preencher("nome", nome);
        terminal.preencher("data", data);
        terminal.escolher("Salvar");
    }

    @When("I select the task {string}")
    public void selecionarTarefa(String nome) {
        contexto.terminal.abrirTarefa(nome);
    }

    @When("I change the task name to {string}")
    public void alterarNome(String nome) {
        contexto.terminal.preencher("nome", nome);
    }
}
