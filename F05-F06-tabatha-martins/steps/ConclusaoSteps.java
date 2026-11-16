package f0506;

import static comum.Verifica.garantir;

import comum.Contexto;
import comum.Terminal;
import io.cucumber.java.pt.Então;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** F05 - Status da tarefa (Concluída / Não concluída). */
public class ConclusaoSteps {
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Contexto contexto;
    private final TarefaAtual atual;

    public ConclusaoSteps(Contexto contexto, TarefaAtual atual) {
        this.contexto = contexto;
        this.atual = atual;
    }

    @Então("o status de conclusão da tarefa deve ser {string} e sem data de conclusão")
    public void statusSemData(String status) {
        Terminal.LinhaTarefa linha = linhaDaTarefa();
        garantir(linha.mostra(status) && linha.colunas().stream().noneMatch(c -> c.startsWith("Concluída em ")),
                "a tarefa \"" + atual.nome + "\" deveria estar \"" + status + "\" e sem data de conclusão:\n"
                        + contexto.terminal.tela());
    }

    @Então("o status de conclusão da tarefa deve ser {string} com a data de conclusão registrada")
    public void statusComData(String status) {
        String esperado = status + " em " + LocalDate.now().format(DATA);
        garantir(linhaDaTarefa().mostra(esperado),
                "a tarefa \"" + atual.nome + "\" deveria mostrar \"" + esperado + "\":\n"
                        + contexto.terminal.tela());
    }

    @Então("devo ver o alerta {string}")
    public void verAlerta(String mensagem) {
        garantir(contexto.terminal.tela().contains("Alerta: " + mensagem),
                "o alerta \"" + mensagem + "\" não aparece na tela:\n" + contexto.terminal.tela());
    }

    private Terminal.LinhaTarefa linhaDaTarefa() {
        Terminal.LinhaTarefa linha = contexto.terminal.tarefas().get(atual.nome);
        garantir(linha != null,
                "a tarefa \"" + atual.nome + "\" não aparece na lista:\n" + contexto.terminal.tela());
        return linha;
    }
}
