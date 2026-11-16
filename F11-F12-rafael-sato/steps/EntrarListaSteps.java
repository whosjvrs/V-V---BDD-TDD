package f1112;

import static comum.Verifica.garantir;

import comum.Contexto;
import comum.Terminal;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EntrarListaSteps {
    private static final String DATA_PADRAO = "15/11/2026";

    private final Contexto contexto;

    public EntrarListaSteps(Contexto contexto) {
        this.contexto = contexto;
    }

    @Given("the list {string} has the task {string}")
    public void tarefaNaLista(String lista, String tarefa) {
        Terminal terminal = contexto.terminal;
        entrarNaLista(lista);
        terminal.escolher("Criar nova tarefa");
        terminal.preencher("nome", tarefa);
        terminal.preencher("data", DATA_PADRAO);
        terminal.escolher("Salvar");
        if (!terminal.titulo().equals(ListaSteps.INICIO)) {
            terminal.escolher("Voltar");
        }
        ListaSteps.garantirNoInicio(terminal);
    }

    @When("I enter the list {string}")
    public void entrarNaLista(String nome) {
        Terminal terminal = contexto.terminal;
        terminal.escolher("Entrar em lista");
        terminal.digitar(nome);
        garantir(terminal.titulo().contains(nome),
                "não entrei na lista \"" + nome + "\":\n" + terminal.tela());
    }

    @Then("I must see the task {string}")
    public void verTarefa(String nome) {
        garantir(contexto.terminal.tarefas().containsKey(nome),
                "a tarefa \"" + nome + "\" não aparece na lista:\n" + contexto.terminal.tela());
    }

    @Then("I must not see the task {string}")
    public void naoVerTarefa(String nome) {
        garantir(!contexto.terminal.tarefas().containsKey(nome),
                "a tarefa \"" + nome + "\" aparece na lista, mas não deveria:\n" + contexto.terminal.tela());
    }

    @Then("I must see the message {string}")
    public void verMensagem(String mensagem) {
        garantir(contexto.terminal.tela().contains(mensagem),
                "a mensagem \"" + mensagem + "\" não aparece na tela:\n" + contexto.terminal.tela());
    }
}
