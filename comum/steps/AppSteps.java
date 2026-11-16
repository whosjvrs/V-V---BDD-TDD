package steps;

import static steps.Verifica.garantir;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;

public class AppSteps {
    private final Contexto contexto;

    public AppSteps(Contexto contexto) {
        this.contexto = contexto;
    }

    @Given("I have opened the app")
    public void abrirApp() throws IOException {
        contexto.terminal = new Terminal();
    }

    @After
    public void fecharApp() {
        if (contexto.terminal != null) {
            contexto.terminal.fechar();
        }
    }

    @When("I select the {string} option")
    public void escolherOpcao(String opcao) {
        contexto.terminal.escolher(opcao);
    }

    @Then("I must see the task {string} in the list")
    public void verTarefaNaLista(String nome) {
        garantir(contexto.terminal.tarefas().containsKey(nome),
                "a tarefa \"" + nome + "\" não aparece na lista:\n" + contexto.terminal.tela());
    }

    @Then("I must not see the task {string} in the list")
    public void naoVerTarefaNaLista(String nome) {
        garantir(!contexto.terminal.tarefas().containsKey(nome),
                "a tarefa \"" + nome + "\" ainda aparece na lista:\n" + contexto.terminal.tela());
    }

    @Then("the task {string} must show the date {string}")
    public void verDataDaTarefa(String nome, String data) {
        Terminal.LinhaTarefa tarefa = contexto.terminal.tarefas().get(nome);
        garantir(tarefa != null && tarefa.data().equals(data),
                "a tarefa \"" + nome + "\" não mostra a data \"" + data + "\":\n" + contexto.terminal.tela());
    }
}
