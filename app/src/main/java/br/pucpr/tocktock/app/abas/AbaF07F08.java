package br.pucpr.tocktock.app.abas;

import br.pucpr.tocktock.app.Aba;
import br.pucpr.tocktock.app.PainelEmConstrucao;
import javafx.scene.Node;

/**
 * Aba de João Victor dos Reis.
 *
 * Só João altera este arquivo. Quando a lógica de F07 e F08 estiver
 * passando nos testes, troque o conteúdo de criarConteudo() pela tela real,
 * chamando as classes do pacote br.pucpr.tocktock.f0708.
 */
public class AbaF07F08 implements Aba {

    @Override
    public String titulo() {
        return "F07 · F08";
    }

    @Override
    public String responsavel() {
        return "João Victor dos Reis";
    }

    @Override
    public String[] funcionalidades() {
        return new String[]{"F07 – Prioridade da tarefa", "F08 – Tarefa recorrente"};
    }

    @Override
    public Node criarConteudo() {
        return PainelEmConstrucao.para(this);
    }
}
