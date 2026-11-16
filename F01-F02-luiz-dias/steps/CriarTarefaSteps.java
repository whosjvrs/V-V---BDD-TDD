package f0102;

import comum.Contexto;
import comum.Terminal;

import static comum.Verifica.garantir;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CriarTarefaSteps {
    private final Contexto contexto;

    public CriarTarefaSteps(Contexto contexto) {
        this.contexto = contexto;
    }

    @Given("I do not have any tasks")
    public void semTarefas() {
        garantir(contexto.terminal.tarefas().isEmpty(),
                "já existem tarefas na lista:\n" + contexto.terminal.tela());
    }

    @Then("I must see the option {string}")
    public void verOpcao(String opcao) {
        garantir(contexto.terminal.opcoes().containsKey(opcao),
                "a opção \"" + opcao + "\" não aparece na tela:\n" + contexto.terminal.tela());
    }

    @When("I fill in the task name with {string}")
    public void preencherNome(String nome) {
        contexto.terminal.preencher("nome", nome);
    }

    @When("I fill in the task date with {string}")
    public void preencherData(String data) {
        contexto.terminal.preencher("data", data);
    }
}
