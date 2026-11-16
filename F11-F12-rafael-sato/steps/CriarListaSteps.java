package f1112;

import static comum.Verifica.garantir;

import comum.Contexto;
import comum.Terminal;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CriarListaSteps {
    private final Contexto contexto;
    private final EstadoListas estado;

    public CriarListaSteps(Contexto contexto, EstadoListas estado) {
        this.contexto = contexto;
        this.estado = estado;
    }

    @Given("There is no list created")
    public void semListas() {
        Terminal terminal = contexto.terminal;
        ListaSteps.garantirNoInicio(terminal);
        garantir(ListaSteps.listas(terminal).isEmpty(),
                "já existem listas criadas:\n" + terminal.tela());
    }

    @When("I create a list with the name {string}")
    public void criarLista(String nome) {
        Terminal terminal = contexto.terminal;
        estado.antes = ListaSteps.listas(terminal);
        ListaSteps.criarLista(terminal, nome);
    }

    @Then("I must see the list {string} on the sidebar")
    public void verLista(String nome) {
        Terminal terminal = contexto.terminal;
        ListaSteps.garantirNoInicio(terminal);
        garantir(ListaSteps.listas(terminal).contains(nome),
                "a lista \"" + nome + "\" não aparece na barra lateral:\n" + terminal.tela());
    }

    @Then("I must see the error {string}")
    public void verErro(String mensagem) {
        Terminal terminal = contexto.terminal;
        garantir(terminal.tela().lines().anyMatch(linha -> linha.strip().equals("Erro: " + mensagem)),
                "o erro \"" + mensagem + "\" não aparece na tela:\n" + terminal.tela());
    }

    @Then("there must not be any new list created")
    public void nenhumaListaNova() {
        Terminal terminal = contexto.terminal;
        ListaSteps.garantirNoInicio(terminal);
        garantir(ListaSteps.listas(terminal).equals(estado.antes),
                "as listas mudaram de " + estado.antes + " para " + ListaSteps.listas(terminal) + ":\n"
                        + terminal.tela());
    }
}
