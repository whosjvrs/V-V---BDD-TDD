package br.pucpr.tocktock.app.abas;

import br.pucpr.tocktock.app.Aba;
import br.pucpr.tocktock.app.PainelEmConstrucao;
import javafx.scene.Node;

/**
 * Aba de Luiz Fernando Simm.
 *
 * Só Luiz altera este arquivo. Quando a lógica de F03 e F04 estiver
 * passando nos testes, troque o conteúdo de criarConteudo() pela tela real,
 * chamando as classes do pacote br.pucpr.tocktock.f0304.
 */
public class AbaF03F04 implements Aba {

    @Override
    public String titulo() {
        return "F03 · F04";
    }

    @Override
    public String responsavel() {
        return "Luiz Fernando Simm";
    }

    @Override
    public String[] funcionalidades() {
        return new String[]{"F03 – Visualizar tarefas", "F04 – Excluir tarefa"};
    }

    @Override
    public Node criarConteudo() {
        return PainelEmConstrucao.para(this);
    }
}
