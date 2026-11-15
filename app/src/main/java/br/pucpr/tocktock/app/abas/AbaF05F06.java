package br.pucpr.tocktock.app.abas;

import br.pucpr.tocktock.app.Aba;
import br.pucpr.tocktock.app.PainelEmConstrucao;
import javafx.scene.Node;

/**
 * Aba de Tabatha Martins.
 *
 * Só Tabatha altera este arquivo. Quando a lógica de F05 e F06 estiver
 * passando nos testes, troque o conteúdo de criarConteudo() pela tela real,
 * chamando as classes do pacote br.pucpr.tocktock.f0506.
 */
public class AbaF05F06 implements Aba {

    @Override
    public String titulo() {
        return "F05 · F06";
    }

    @Override
    public String responsavel() {
        return "Tabatha Martins";
    }

    @Override
    public String[] funcionalidades() {
        return new String[]{"F05 – Status da tarefa (Concluída / Não concluída)", "F06 – Status da tarefa (Farei / Não farei)"};
    }

    @Override
    public Node criarConteudo() {
        return PainelEmConstrucao.para(this);
    }
}
