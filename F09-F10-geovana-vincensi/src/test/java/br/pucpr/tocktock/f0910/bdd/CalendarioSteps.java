package br.pucpr.tocktock.f0910.bdd;

import io.cucumber.java.After;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class CalendarioSteps {
    private Terminal terminal;

    @Dado("que eu abri a aplicação")
    public void abrir() throws IOException {
        terminal = new Terminal();
        assertEquals("TockTock - Início", terminal.titulo(), terminal.tela());
        assertTrue(terminal.tarefas().isEmpty(), "O cenário requer uma sessão sem tarefas: " + terminal.tela());
    }

    @After
    public void fechar() {
        if (terminal != null) terminal.fechar();
    }

    @Dado("existe uma tarefa com o nome {string} com a data {string}")
    public void criar(String nome, String data) {
        terminal.escolher("Criar nova tarefa");
        terminal.preencher("nome", nome);
        if (!data.isEmpty()) terminal.preencher("data", data);
        terminal.escolher("Salvar");
        var tarefa = terminal.tarefas().get(nome);
        assertNotNull(tarefa, "Preparação: tarefa não foi criada: " + terminal.tela());
        assertEquals(data, tarefa.data(), "Preparação: data incorreta: " + terminal.tela());
    }

    @Dado("existe uma tarefa com o nome {string} sem nenhuma data")
    public void criarSemData(String nome) { criar(nome, ""); }

    @Dado("estou na tela do calendário")
    public void calendario() { terminal.escolher("Calendário"); }

    @Quando("eu consulto o dia {string} do calendário")
    public void dia(String data) { terminal.preencher("dia", data); }

    @Quando("eu vejo o calendário do mês {string}")
    public void mes(String mes) { terminal.preencher("mês", mes); }

    @Então("devo ver a tarefa {string}")
    public void presente(String nome) {
        assertTrue(terminal.tarefas().containsKey(nome), terminal.tela());
    }

    @Então("não devo ver a tarefa {string} em nenhum dia")
    public void ausenteNoMes(String nome) { ausente(nome); }

    @Então("não devo ver a tarefa {string}")
    public void ausente(String nome) {
        assertFalse(terminal.tarefas().containsKey(nome), terminal.tela());
    }

    @Quando("eu filtro as tarefas de {string} até {string}")
    public void filtrar(String inicio, String fim) {
        terminal.preencher("data inicial", inicio);
        terminal.preencher("data final", fim);
        terminal.escolher("Filtrar");
    }

    @Então("eu devo ver as tarefas {string} e {string}")
    public void duas(String primeira, String segunda) {
        presente(primeira);
        presente(segunda);
        assertEquals(2, terminal.tarefas().size(), terminal.tela());
    }

    @Então("devo ver a mensagem {string}")
    public void mensagem(String mensagem) {
        assertTrue(terminal.tela().lines().anyMatch(linha -> linha.strip().equals(mensagem)), terminal.tela());
        assertTrue(terminal.tarefas().isEmpty(), terminal.tela());
    }
}
