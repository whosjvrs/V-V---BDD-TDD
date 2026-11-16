package f0910;

import comum.Contexto;
import comum.Terminal;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import java.io.IOException;
import static comum.Verifica.garantir;

public class CalendarioSteps {
    private final Contexto contexto;
    private Terminal terminal;

    public CalendarioSteps(Contexto contexto) {
        this.contexto = contexto;
    }

    @Dado("que eu abri a aplicação")
    public void abrir() throws IOException {
        contexto.terminal = new Terminal();
        terminal = contexto.terminal;
        garantir("TockTock - Início".equals(terminal.titulo()), terminal.tela());
        garantir(terminal.tarefas().isEmpty(), "O cenário requer uma sessão sem tarefas: " + terminal.tela());
    }

    @Dado("existe uma tarefa com o nome {string} com a data {string}")
    public void criar(String nome, String data) {
        terminal.escolher("Criar nova tarefa");
        terminal.preencher("nome", nome);
        if (!data.isEmpty()) terminal.preencher("data", data);
        terminal.escolher("Salvar");
        var tarefa = terminal.tarefas().get(nome);
        garantir(tarefa != null, "Preparação: tarefa não foi criada: " + terminal.tela());
        garantir(data.equals(tarefa.data()), "Preparação: data incorreta: " + terminal.tela());
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
        garantir(terminal.tarefas().containsKey(nome), terminal.tela());
    }

    @Então("não devo ver a tarefa {string} em nenhum dia")
    public void ausenteNoMes(String nome) { ausente(nome); }

    @Então("não devo ver a tarefa {string}")
    public void ausente(String nome) {
        garantir(!terminal.tarefas().containsKey(nome), terminal.tela());
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
        garantir(terminal.tarefas().size() == 2, terminal.tela());
    }

    @Então("devo ver a mensagem {string}")
    public void mensagem(String mensagem) {
        garantir(terminal.tela().lines().anyMatch(linha -> linha.strip().equals(mensagem)), terminal.tela());
        garantir(terminal.tarefas().isEmpty(), terminal.tela());
    }
}
