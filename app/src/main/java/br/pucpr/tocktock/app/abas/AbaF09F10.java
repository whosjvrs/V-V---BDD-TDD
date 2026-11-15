package br.pucpr.tocktock.app.abas;

import br.pucpr.tocktock.app.Aba;
import br.pucpr.tocktock.app.PainelEmConstrucao;
import javafx.scene.Node;

/**
 * Aba de Geovana Vincensi.
 *
 * Só Geovana altera este arquivo. Quando a lógica de F09 e F10 estiver
 * passando nos testes, troque o conteúdo de criarConteudo() pela tela real,
 * chamando as classes do pacote br.pucpr.tocktock.f0910.
 */
public class AbaF09F10 implements Aba {

    @Override
    public String titulo() {
        return "F09 · F10";
    }

    @Override
    public String responsavel() {
        return "Geovana Vincensi";
    }

    @Override
    public String[] funcionalidades() {
        return new String[]{"F09 – Exibição de tarefa no calendário", "F10 – Filtro por período de datas"};
    }

    @Override
    public Node criarConteudo() {
        return PainelEmConstrucao.para(this);
    }
}
