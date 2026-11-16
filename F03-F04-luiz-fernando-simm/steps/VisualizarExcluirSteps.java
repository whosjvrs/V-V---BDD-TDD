package f0304;

import comum.Contexto;
import comum.Terminal;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;

import java.io.IOException;

import static comum.Verifica.garantir;

public class VisualizarExcluirSteps {
    private final Contexto contexto;

    public VisualizarExcluirSteps(Contexto contexto) {
        this.contexto = contexto;
    }

    @Dado("que o usuário esteja na tela inicial")
    public void iniciar() throws IOException {
        contexto.terminal = new Terminal();
        garantir(contexto.terminal.titulo().equals("TockTock - Início"),
                "a tela inicial não foi exibida:\n" + contexto.terminal.tela());
    }

    @Dado("tenha a tarefa {string} criada")
    public void cadastrar(String nome) {
        contexto.terminal.escolher("Criar nova tarefa");
        contexto.terminal.preencher("nome", nome);
        contexto.terminal.preencher("data", "15/11/2026");
        contexto.terminal.escolher("Salvar");
        garantir(contexto.terminal.tarefas().containsKey(nome),
                "a tarefa de preparação não aparece na lista:\n" + contexto.terminal.tela());
    }

    @Quando("eu clico na tela {string}")
    public void abrirParaVisualizar(String nome) {
        contexto.terminal.abrirTarefa(nome);
    }

    @Então("a tarefa {string} tem seus dados exibidos na tela")
    public void verificarDetalhes(String nome) {
        String tela = contexto.terminal.tela();
        garantir(tela.contains(nome) && tela.contains("15/11/2026"),
                "os detalhes devem mostrar nome e data da tarefa:\n" + tela);
    }

    @Quando("o usuário clicar na tarefa {string}")
    public void abrirParaExcluir(String nome) {
        contexto.terminal.abrirTarefa(nome);
    }

    @Quando("clicar na opção {string}")
    public void excluir(String opcao) {
        contexto.terminal.escolher(opcao);
    }

    @Então("a tarefa {string} não deverá mais aparecer na lista de tarefas")
    public void verificarAusencia(String nome) {
        garantir(contexto.terminal.titulo().equals("TockTock - Início"),
                "a exclusão deve retornar à lista inicial:\n" + contexto.terminal.tela());
        garantir(!contexto.terminal.tarefas().containsKey(nome),
                "a tarefa excluída ainda aparece na lista:\n" + contexto.terminal.tela());
    }
}
