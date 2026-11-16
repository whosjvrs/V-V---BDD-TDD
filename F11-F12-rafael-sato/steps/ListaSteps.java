package f1112;

import static comum.Verifica.garantir;

import comum.Contexto;
import comum.Terminal;

import io.cucumber.java.en.Given;
import java.io.IOException;
import java.util.List;

public class ListaSteps {
    static final String INICIO = "TockTock - Início";
    private static final String PREFIXO_LISTA = "Lista: ";

    private final Contexto contexto;

    public ListaSteps(Contexto contexto) {
        this.contexto = contexto;
    }

    @Given("I am logged in")
    public void logado() throws IOException {
        contexto.terminal = new Terminal();
    }

    @Given("there is one list named {string}")
    public void listaExistente(String nome) {
        Terminal terminal = contexto.terminal;
        criarLista(terminal, nome);
        garantir(listas(terminal).contains(nome),
                "a lista \"" + nome + "\" não foi criada:\n" + terminal.tela());
    }

    static void criarLista(Terminal terminal, String nome) {
        terminal.escolher("Criar lista");
        terminal.digitar(nome);
    }

    static List<String> listas(Terminal terminal) {
        return terminal.tela().lines()
                .map(String::strip)
                .filter(linha -> linha.startsWith(PREFIXO_LISTA))
                .map(linha -> linha.substring(PREFIXO_LISTA.length()))
                .toList();
    }

    static void garantirNoInicio(Terminal terminal) {
        garantir(terminal.titulo().equals(INICIO),
                "não estou no menu principal:\n" + terminal.tela());
    }
}
